package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.*;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.*;
import com.bintoufha.gestionStocks.services.CommandeFournisseurService;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
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
    public CommandeFournisseursDto save(CommandeFournisseursDto commandeFournisseursDto) {
        return null;
    }

    @Override
    public CommandeFournisseursDto findByUUID(UUID uuid) {
        return null;
    }

    @Override
    public CommandeFournisseursDto findByReference(String reference) {
        return null;
    }

    @Override
    public List<CommandeFournisseursDto> findAll() {
        return List.of();
    }

    @Override
    public void delete(UUID uuid) {

    }
    @Override
    public CommandeFournisseursDto UpdteEtatCommande(UUID uuidCommande, EtatCommande etatCommande) {
        CheckUuidCommande(uuidCommande);
        if (etatCommande == null || EtatCommande.INCONNU.equals(etatCommande)) {
            log.error("etat de la commande fournisseur est null");
            throw new InvalidOperationException("Impossible de modifier etat de la commande avec etat null ou invalide"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeFournisseursDto cmdFrstDto = CheckEtatCommande(uuidCommande);
        cmdFrstDto.setEtatCommande(etatCommande);

        CommandeFournisseurs saveCommandeFournisseurs = commandeFournisseursRepository.save(
                CommandeFournisseursDto.toEntity(cmdFrstDto));
        if (cmdFrstDto.isCommandeLivree()) {
            updateMvStock(uuidCommande);
        }
        return CommandeFournisseursDto.fromEntity(saveCommandeFournisseurs);
    }

    @Override
    public CommandeFournisseursDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        CheckUuidCommande(uuidCommande);
        CheckUuidLigneCommande(uuidLigneCommande);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("quantite ne peut pas être null ou égale à zéro");
            throw new InvalidOperationException("Impossible de modifier une ligne de commande avec une quantité nulle ou égale à zéro"
                    , ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseursDto commandeFournisseursDto = CheckEtatCommande(uuidCommande);

        Optional<LigneCommandeFournisseurs> ligneCommandeFournisseursOptional = findLigneFournisseurs(uuidLigneCommande);
        LigneCommandeFournisseurs ligneCommandeFournisseurs = ligneCommandeFournisseursOptional.get();
        ligneCommandeFournisseurs.setQuantite(quantite);
        ligneCommandeFournisseursRepository.save(ligneCommandeFournisseurs);
        return commandeFournisseursDto;
    }

    @Override
    public CommandeFournisseursDto UpdateFournisseurs(UUID uuidCommande, UUID uuidfournisseur) {
        CheckUuidCommande(uuidCommande);
        if (uuidfournisseur == null) {
            log.error("identifiant du client est null");
            throw new InvalidOperationException("Impossible de modifier une commande avec identifiant client null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeFournisseursDto commandeFournisseursDto = CheckEtatCommande(uuidCommande);
        Optional<Fournisseurs> fournisseursOptional = fournisseursRepository.findByUuid(uuidfournisseur);
        if (fournisseursOptional.isEmpty()) {
            throw new EntityNoFoundException("Aucun fournisseurs n'a été trouver avec identifiant " + uuidfournisseur
                    , ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        // On injecte le fournisseur dans le DTO (conversion Entity -> DTO)
        commandeFournisseursDto.setFournisseurs(FournisseursDto.fromEntity(fournisseursOptional.get()));

        // Conversion DTO -> Entity avant sauvegarde
        CommandeFournisseurs entityCommandeFournisseurs = commandeFournisseursDto.toEntity(commandeFournisseursDto);

        // Sauvegarde dans la DB
        CommandeFournisseurs savedCommandeFournisseurs = commandeFournisseursRepository.save(entityCommandeFournisseurs);

        // Conversion Entity -> DTO avant retour
        return commandeFournisseursDto.fromEntity(savedCommandeFournisseurs);
    }

    @Override
    public CommandeFournisseursDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        // Vérifie si la commande existe via son UUID
        CheckUuidCommande(uuidCommande);

        // Vérifie si la ligne de commande existe via son UUID
        CheckUuidLigneCommande(uuidLigneCommande);

        // Vérifie si le nouvel article existe via son UUID
        CheckUuidArticle(newdUuidArticle, "nouvel");

        // Vérifie l'état de la commande (par ex. non livrée ou modifiable)
        CommandeFournisseursDto commandeFournisseursDto = CheckEtatCommande(uuidCommande);

        // Recherche la ligne de commande concernée par l'UUID
        Optional<LigneCommandeFournisseurs> ligneCommandeFournisseursOptional = findLigneFournisseurs(uuidLigneCommande);

        // Recherche l'article correspondant au nouvel UUID
        Optional<Articles> articlesOptional = articleRepository.findByUuid(newdUuidArticle);

        // Si aucun article n’est trouvé → exception
        if (articlesOptional.isEmpty()) {
            throw new EntityNoFoundException(
                    "Aucun article n'a ete trouve avec identifiant " + newdUuidArticle,
                    ErrorCodes.ARTICLE_NOT_FOUND
            );
        }
        // Validation de l’article trouvé (contrôles métier, champs obligatoires, etc.)
        List<String> errors = ArticleValidator.validate(ArticlesDto.fromEntity(articlesOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalEntityException(
                    "Article est invalid",
                    ErrorCodes.ARTICLE_NOT_VALID,
                    errors
            );
        }
        // Récupération de la ligne de commande existante
        LigneCommandeFournisseurs ligneCommandeFournisseurToSave = ligneCommandeFournisseursOptional.get();

        // Mise à jour de l’article de la ligne de commande
        ligneCommandeFournisseurToSave.setArticles(articlesOptional.get());

        // Sauvegarde de la ligne de commande mise à jour en base
        ligneCommandeFournisseursRepository.save(ligneCommandeFournisseurToSave);

        // Retourne la commande (DTO) mise à jour
        return commandeFournisseursDto;
    }

    @Override
    public CommandeFournisseursDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {
        // Vérifie si la commande existe via son UUID
        CheckUuidCommande(uuidCommande);
        // Vérifie si la ligne de commande existe via son UUID
        CheckUuidLigneCommande(uuidLigneCommande);

        CommandeFournisseursDto commandeFournisseurs = CheckEtatCommande(uuidCommande);

        findLigneFournisseurs(uuidLigneCommande);
        ligneCommandeFournisseursRepository.deleteByUuid(uuidLigneCommande);
        return commandeFournisseurs;
    }

    @Override
    public List<LigneCommandeFournisseursDto> findAllLigneCommandeFournisseurByUuid(UUID uuidCommande) {
        return List.of();
    }

    private void CheckUuidCommande(UUID uuidCommande) {
        if (uuidCommande == null) {
            log.error("identifiant de la commande client est null");
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

    private CommandeFournisseursDto CheckEtatCommande(UUID uuidCommande) {
        CommandeFournisseursDto fournisseursDto = findByUUID(uuidCommande);
        if (fournisseursDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier une commande deja livree"
                    , ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return fournisseursDto;
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
                    .idEntreprise(ligne.getIdEntreprise())
                    .dateMvt(Instant.now())
                    .build();
            mvtService.entreeStock(mvtStock);
        });
    }
}
