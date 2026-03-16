package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.dto.mouvement.MouvementStocksDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.CommandeFournisseurMapper;
import com.bintoufha.gestionStocks.mapper.LigneCommandeFournisseurMapper;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.*;
import com.bintoufha.gestionStocks.services.CommandeFournisseurService;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final LigneCommandeFournisseursRepository ligneCommandeFournisseursRepository;
    private CommandeFournisseursRepository commandeFournisseursRepository;
    private FournisseursRepository fournisseursRepository;
    private ArticleRepository articleRepository;
    private MouvementStockService mvtService;

    @Autowired
    public CommandeFournisseurServiceImpl(
            CommandeFournisseursRepository commandeFournisseursRepository, ArticleRepository articleRepository,
            FournisseursRepository fournisseursRepository,
            LigneCommandeFournisseursRepository ligneCommandeFournisseursRepository,
            MouvementStockService mvtService) {
        this.commandeFournisseursRepository = commandeFournisseursRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseursRepository = ligneCommandeFournisseursRepository;
        this.fournisseursRepository = fournisseursRepository;
        this.mvtService = mvtService;
    }

    @Override
    public CommandeFournisseurSaveDto save(CommandeFournisseurSaveDto commandeFournisseursDto) {
        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseursDto);
        if (!errors.isEmpty()) {
            log.warn("la commande de cette client n'est pas valide");
            throw new InvalEntityException("la commande du client n'est pas valide",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }
        if (commandeFournisseursDto.getEtatCommande() != null && commandeFournisseursDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est " +
                    "est livre", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        Optional<Fournisseurs> fournisseurs = fournisseursRepository.findByUuid(commandeFournisseursDto.getFournisseursUuid());
        if (fournisseurs.isEmpty()) {
            log.warn("client avec identifiant {} n'existe pas dans la base de donne",
                    commandeFournisseursDto.getFournisseursUuid());
            throw new EntityNoFoundException("Aucun fournisseurs  avec cette identifiant : "
                    + commandeFournisseursDto.getFournisseursUuid() + "n'a ete trouve dans la base",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        List<String> ArticleError = new ArrayList<>();
        if (commandeFournisseursDto.getLigneCommandeFournisseurs() != null) {
            commandeFournisseursDto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getArticle().getUuid()  != null) {
                    Optional<Articles> article = articleRepository.findByUuid(ligCmdFrs.getArticle().getUuid() );
                    if (article.isEmpty()) {
                        ArticleError.add("l'article avec identifiant : " + ligCmdFrs.getArticle().getUuid()  + "n'existe pas");
                    }
                } else {
                    ArticleError.add("Impossible d'enregistre un commande avec article null");
                }
            });
        }

        if (!ArticleError.isEmpty()) {
            log.warn("");
            throw new InvalEntityException("Article n'existe pas dans la base de donnee", ErrorCodes.ARTICLE_NOT_FOUND, ArticleError);
        }

        CommandeFournisseurs saveCmdClt = commandeFournisseursRepository.save
                (CommandeFournisseurMapper.toEntity(commandeFournisseursDto, fournisseurs.get()));
        if (commandeFournisseursDto.getLigneCommandeFournisseurs() != null) {
            commandeFournisseursDto.getLigneCommandeFournisseurs().forEach(ligDtoFrs -> {
                // --- Sécurité : vérifier que l'article est bien renseigné ---
                if (ligDtoFrs.getArticle().getUuid() == null) {
                    throw new InvalidOperationException(
                            "Ligne de commande fournisseur sans article valide",
                            ErrorCodes.ARTICLE_NOT_VALID
                    );
                }
                Articles article = articleRepository.findByUuid(ligDtoFrs.getArticle().getUuid() )
                        .orElseThrow(() -> new EntityNoFoundException(
                                "Article introuvable",
                                ErrorCodes.ARTICLE_NOT_FOUND
                        ));

                LigneCommandeFournisseurs ligne =
                        LigneCommandeFournisseurMapper.toEntity(ligDtoFrs, article);

                ligne.setCommandefournisseur(saveCmdClt);

                ligneCommandeFournisseursRepository.save(ligne);
            });
        }

        return CommandeFournisseurMapper.fromEntity(saveCmdClt);
    }

    @Override
    public CommandeFournisseursListDto findByUUID(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant de la commande fournisseur est null");
            throw new IllegalArgumentException("L'identifiant UUID ne peut pas être null");
            //return null;
        }

        // Récupération du commande fournissseur ou exception si non trouvé
        CommandeFournisseurs commandeFournisseurs = commandeFournisseursRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun fournisseur avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return CommandeFournisseurMapper.toListDto(commandeFournisseurs);
    }

    @Override
    public CommandeFournisseursListDto findByReference(String reference) {
        if (reference == null) {
            log.error("Reference de la commande client est null");
            throw new IllegalArgumentException("Reference ne peut pas être null");
        }

        CommandeFournisseurs commandeFournisseurs = commandeFournisseursRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucune commande client n'a ete trouve avec la refernce : " +
                                reference, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return CommandeFournisseurMapper.toListDto(commandeFournisseurs);
    }

    @Override
    public List<CommandeFournisseursListDto> findAll() {
        return commandeFournisseursRepository.findAll()
                .stream()
                .map(commandeFournisseurs -> {
                    // ou via TarificationService si besoin
                    return CommandeFournisseurMapper.toListDto(commandeFournisseurs);
                })
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("Commande fournisseur ID is NULL");
            return;
        }
        List<LigneCommandeFournisseurs> ligneCommandeFournisseurs = ligneCommandeFournisseursRepository.findAllByArticles_Uuid(uuid);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseursRepository.deleteById(uuid);
    }
    @Override
    public CommandeFournisseursListDto UpdteEtatCommande(UUID uuidCommande, EtatCommande etatCommande) {
        // 1️⃣ Vérification basique : UUID obligatoire
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérification de la validité du nouvel état
        if (etatCommande == null || EtatCommande.INCONNU.equals(etatCommande)) {
            log.error("Etat de la commande client invalide");
            throw new InvalidOperationException(
                    "Impossible de modifier l'état de la commande avec un état null ou invalide",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        // 3️⃣ Récupération de la COMMANDE EN ENTITY depuis la base
        // + vérification qu'elle n'est pas déjà livrée
        CommandeFournisseurs commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Mise à jour UNIQUEMENT de l'état
        // (on ne touche ni au client, ni aux lignes, ni à la référence)
        commande.setEtatCommande(etatCommande);

        // 5️⃣ Sauvegarde
        // JPA comprend qu'il s'agit d'un UPDATE
        CommandeFournisseurs savedEntity =
                commandeFournisseursRepository.save(commande);

        // 6️⃣ Mouvement de stock UNIQUEMENT si on passe à LIVREE
        if (EtatCommande.LIVREE.equals(etatCommande)) {
            updateMvStock(uuidCommande);
        }

        // 7️⃣ Retour d'un DTO de LECTURE
        return CommandeFournisseurMapper.toListDto(savedEntity);
    }

    @Override
    public CommandeFournisseursListDto UpdateQuantiteCommande(
            UUID uuidCommande,
            UUID uuidLigneCommande,
            BigDecimal quantite
    ) {
        // 1️⃣ Vérifie que la commande existe
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifie que la ligne de commande existe
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Validation métier de la quantité
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Quantité invalide pour la ligne de commande");
            throw new InvalidOperationException(
                    "Impossible de modifier une ligne de commande avec une quantité nulle ou négative",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        // 4️⃣ Vérifie que la commande est modifiable (ex: non LIVRÉE)
        CommandeFournisseurs commande = CheckEtatCommande(uuidCommande);

        // 5️⃣ Recherche de la ligne de commande
        LigneCommandeFournisseurs ligneCommande = findLigneFournisseurs(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // 6️⃣ Mise à jour de la quantité
        ligneCommande.setQuantite(quantite);

        // 7️⃣ Sauvegarde de la ligne modifiée
        ligneCommandeFournisseursRepository.save(ligneCommande);

        // 8️⃣ Retour de la commande (DTO)
        return CommandeFournisseurMapper.toListDto(commande);
    }

    @Override
    public CommandeFournisseursListDto UpdateFournisseurs(UUID uuidCommande, UUID uuidfournisseur) {
        // 1️⃣ Vérifier que l'UUID de la commande est fourni
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifier que l'UUID du client est fourni
        if (uuidfournisseur == null) {
            log.error("identifiant du fournisseur est null");
            throw new InvalidOperationException(
                    "Impossible de modifier une commande avec identifiant client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        // 3️⃣ Charger la commande EN ENTITY
        // + vérifier qu'elle n'est pas déjà livrée
        CommandeFournisseurs commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Charger le nouveau client depuis la base
        Fournisseurs fournisseurs = fournisseursRepository.findByUuid(uuidfournisseur)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun client trouvé avec l'identifiant " + uuidfournisseur,
                        ErrorCodes.CLIENT_NOT_FOUND
                ));

        // 5️⃣ Mettre à jour UNIQUEMENT le client de la commande
        commande.setFournisseurs(fournisseurs);

        // 6️⃣ Sauvegarder la commande mise à jour
        // JPA fera un UPDATE
        CommandeFournisseurs savedCommande =
                commandeFournisseursRepository.save(commande);

        // 7️⃣ Retourner un DTO de lecture
        return CommandeFournisseurMapper.toListDto(savedCommande);
    }

    @Override
    public CommandeFournisseursListDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        // 1️⃣ Vérifie que la commande existe
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifie que la ligne de commande existe
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Vérifie que le nouvel article existe
        CheckUuidArticle(newdUuidArticle, "nouvel");

        // 4️⃣ Vérifie que la commande est modifiable (ex: non LIVRÉE)
        CommandeFournisseurs commande = CheckEtatCommande(uuidCommande);

        // 5️⃣ Récupération de la ligne de commande
        LigneCommandeFournisseurs ligneCommande = findLigneFournisseurs(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // 6️⃣ Récupération du nouvel article
        Articles article = articleRepository.findByUuid(newdUuidArticle)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun article trouvé avec l'identifiant " + newdUuidArticle,
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));

        // 7️⃣ Mise à jour de l’article dans la ligne de commande
        ligneCommande.setArticles(article);

        // 8️⃣ Sauvegarde de la ligne modifiée
        ligneCommandeFournisseursRepository.save(ligneCommande);

        // 9️⃣ Retourne la commande mise à jour
        return CommandeFournisseurMapper.toListDto(commande);
    }

    @Override
    public CommandeFournisseursListDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {

        // 1️⃣ Vérifier que l'UUID de la commande est valide
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifier que l'UUID de la ligne de commande est valide
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Charger la commande EN ENTITY + vérifier qu'elle n'est pas déjà livrée
        CommandeFournisseurs commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Charger la ligne de commande via son UUID
        LigneCommandeFournisseurs ligne = ligneCommandeFournisseursRepository.findByUuid(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // 5️⃣ Vérifier que la ligne appartient bien à la commande
        if (!ligne.getCommandefournisseur().getUuid().equals(uuidCommande)) {
            throw new InvalidOperationException(
                    "La ligne de commande n'appartient pas à cette commande",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        // 6️⃣ Supprimer la ligne de commande
        ligneCommandeFournisseursRepository.delete(ligne);

        // 7️⃣ Recharger la commande après suppression pour refléter les changements
        // ✅ Important : on récupère l'entité complète avec toutes ses lignes
        CommandeFournisseurs commandeUpdated =
                commandeFournisseursRepository.findByUuid(uuidCommande)
                        .orElseThrow(() -> new EntityNoFoundException(
                                "Commande fournisseur introuvable",
                                ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                        ));

        // 8️⃣ Retourner un DTO de lecture à jour
        // Assurez-vous que la méthode Mapper prend bien une Entity
        return CommandeFournisseurMapper.toListDto(commandeUpdated);
    }


//    @Override
//    public List<LigneCommandeClientSaveDto> findAllLigneCommandeFournisseurByUuid(UUID uuidCommande) {
//        return List.of();
//    }

    private void CheckUuidCommande(UUID uuidCommande) {
        if (uuidCommande == null) {
            log.error("identifiant de la commande fournisseurs est null");
            throw new InvalidOperationException("Impossible de modifier la commande avec identifiant null"
                    , ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void CheckUuidLigneCommande(UUID uuidLigneCommande) {
        if (uuidLigneCommande == null) {
            log.error("identifiant de la ligne de commande est null");
            throw new InvalidOperationException("Impossible de modifier une ligne de commande avec identifiant null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

    }

    private CommandeFournisseurs CheckEtatCommande(UUID uuidCommande) {

        CommandeFournisseurs commande = commandeFournisseursRepository.findByUuid(uuidCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Commande fournisseurs introuvable",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));

        // Interdiction de modifier une commande déjà livrée
        if (EtatCommande.LIVREE.equals(commande.getEtatCommande())) {
            throw new InvalidOperationException(
                    "Impossible de modifier une commande déjà livrée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        return commande;
    }

    private Optional<LigneCommandeFournisseurs> findLigneFournisseurs(UUID uuidLigneCommande) {
        Optional<LigneCommandeFournisseurs> ligneCommandeFournisseursOptional = ligneCommandeFournisseursRepository.findByUuid(uuidLigneCommande);
        if (ligneCommandeFournisseursOptional.isEmpty()) {
            throw new EntityNoFoundException(
                    "Aucune ligne de commande client n'a ete trouve avec identifiant " + uuidLigneCommande
                    , ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeFournisseursOptional;
    }

    private void CheckUuidArticle(UUID uuidArticle, String msg) {
        if (uuidArticle == null) {
            log.error("identifiant de " + msg + " est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de commande avec un" + msg + " identifiant article  null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    // methode pour mettre a jour le stock pour Commande fournisseurs
    private void updateMvStock(UUID uuidCommande) {
        List<LigneCommandeFournisseurs> ligneCommandeFournisseurs = ligneCommandeFournisseursRepository.findAllByArticles_Uuid(uuidCommande);
        ligneCommandeFournisseurs.forEach(ligne -> {
            MouvementStocksDto mvtStock = MouvementStocksDto.builder()
                    .article(ligne.getArticles())
                    .typeMouvement(TypeMvtStocks.ENTREE)
                    .sourceMouvement(SourceMvtStocks.COMMENT_FOURNISSEUR)
                    .quantite(ligne.getQuantite())
                    //.idEntreprise(ligne.getIdEntreprise())
                    .dateMvt(Instant.now())
                    .build();
            mvtService.entreeStock(mvtStock);
        });
    }
}
