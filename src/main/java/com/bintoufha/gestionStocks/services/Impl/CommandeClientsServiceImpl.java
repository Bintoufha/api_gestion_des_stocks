package com.bintoufha.gestionStocks.services.Impl;

//import com.bintoufha.gestionStocks.dto.ClientsDto;

import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientListDto;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.mouvement.MouvementStocksDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.CommandeClientMapper;
import com.bintoufha.gestionStocks.mapper.LigneCommandeClientMapper;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import com.bintoufha.gestionStocks.repository.LigneCommandeClientsRepository;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.validator.CommandClientsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientsServiceImpl implements CommandeClientsService {

    private final LigneCommandeClientsRepository ligneCommandeClientsRepository;
    private CommandeClientsRepository commandeClientsRepository;
    private ClientsRepository clientsRepository;
    private ArticleRepository articleRepository;
    private MouvementStockService mvtService;
    //private EtatCommande etatCommande;

    /****************************************** initialisation du constructeur ****************************************/
    @Autowired
    public CommandeClientsServiceImpl(
            CommandeClientsRepository commandeClientsRepository,
            ClientsRepository clientsRepository,
            ArticleRepository articleRepository,
            LigneCommandeClientsRepository ligneCommandeClientsRepository,
            MouvementStockService mvtService) {
        this.commandeClientsRepository = commandeClientsRepository;
        this.clientsRepository = clientsRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientsRepository = ligneCommandeClientsRepository;
        this.mvtService = mvtService;
    }

    /****************************************** Methode CRUD de la commande clients ****************************************/

// enregistre un commande client
    @Transactional
    @Override
    public CommandeClientSaveDto save(CommandeClientSaveDto commandeClientsDto) {

        List<String> errors = CommandClientsValidator.validate(commandeClientsDto);
        if (!errors.isEmpty()) {
            log.warn("la commande de cette client n'est pas valide");
            throw new InvalEntityException("la commande du client n'est pas valide",
                    ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        if (commandeClientsDto.getEtatCommande() != null && commandeClientsDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est " +
                    "est livre", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<Clients> clients = clientsRepository.findByUuid(commandeClientsDto.getClients());
        if (clients.isEmpty()) {
            log.warn("client avec identifiant {} n'existe pas dans la base de donne",
                    commandeClientsDto.getClients());
            throw new EntityNoFoundException("Aucun client  avec cette identifiant : "
                    + commandeClientsDto.getClients() + "n'a ete trouve dans la base",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }
        List<String> ArticleError = new ArrayList<>();
        if (commandeClientsDto.getLigneCommandeClients() != null) {
            commandeClientsDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticle().getUuid() != null) {
                    Optional<Articles> article = articleRepository.findByUuid(ligCmdClt.getArticle().getUuid());
                    if (article.isEmpty()) {
                        ArticleError.add("l'article avec identifiant : " + ligCmdClt.getArticle().getUuid() + "n'existe pas");
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

        CommandeClients saveCmdClt = commandeClientsRepository.save
                (CommandeClientMapper.toEntity(commandeClientsDto, clients.get()));
        if (commandeClientsDto.getLigneCommandeClients() != null) {
            commandeClientsDto.getLigneCommandeClients().forEach(ligDto -> {

                Articles article = articleRepository.findByUuid(ligDto.getArticle().getUuid())
                        .orElseThrow(() -> new EntityNoFoundException(
                                "Article introuvable",
                                ErrorCodes.ARTICLE_NOT_FOUND
                        ));

                LigneCommandeClients ligne = LigneCommandeClientMapper.toEntity(ligDto, article);
                ligne.setCommandeClients(saveCmdClt);
                ligneCommandeClientsRepository.save(ligne);
            });
        }

        return CommandeClientMapper.fromEntity(saveCmdClt);
    }

    // chercher un commande client par son UUID
    @Transactional
    @Override
    public CommandeClientListDto findByUUID(UUID uuid) {

        if (uuid == null) {
            log.error("identifiant de la commande client est null");
            throw new IllegalArgumentException("L'identifiant UUID ne peut pas être null");
            //return null;
        }

        // Récupération du commande client ou exception si non trouvé
        CommandeClients commandeClients = commandeClientsRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun clients avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return CommandeClientMapper.toListDto(commandeClients);
    }

    // chercher un commande client par son Reference
    @Override
    @Transactional
    public CommandeClientListDto findByReference(String reference) {
        if (reference == null) {
            log.error("Reference de la commande client est null");
            throw new IllegalArgumentException("Reference ne peut pas être null");
        }

        CommandeClients commandeClients = commandeClientsRepository.findByRefernce(reference)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucune commande client n'a ete trouve avec la refernce : " +
                                reference, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return CommandeClientMapper.toListDto(commandeClients);
    }

    // recuperer tous les commande client par pour une entreprise donnee
    @Transactional
    @Override
    public List<CommandeClientListDto> findAll() {
        return commandeClientsRepository.findAll()
                .stream()
                .map(commandeClients -> {
                    // ou via TarificationService si besoin
                    return CommandeClientMapper.toListDto(commandeClients);
                })
                .toList();
    }

    // supprimmer un commande client par son UUID
    @Transactional
    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant de la commande client est null");
            throw new IllegalArgumentException("identifiant ne peut pas être null");
        }
        List<LigneCommandeClients> ligneCommandeClients = ligneCommandeClientsRepository.findCommandeClientsByUuid(uuid);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande client deja utilisee",
                    ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        commandeClientsRepository.deleteByUuid(uuid);
    }

    // recuperer tous les ligneCommande client par pour une commande donnee
    @Transactional
    @Override
    public List<LigneCommandeClientSaveDto> findAllLignesCommandeClientsByUuid(UUID uuidCommande) {
        return ligneCommandeClientsRepository.findCommandeClientsByUuid(uuidCommande).stream()
                .map(LigneCommandeClientMapper::fromEntity)
                .collect(Collectors.toList());
    }

    // Modifier l'etat de la commande
    @Transactional
    @Override
    public CommandeClientListDto UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande) {

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
        CommandeClients commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Mise à jour UNIQUEMENT de l'état
        // (on ne touche ni au client, ni aux lignes, ni à la référence)
        commande.setEtatCommande(etatCommande);

        // 5️⃣ Sauvegarde
        // JPA comprend qu'il s'agit d'un UPDATE
        CommandeClients savedEntity =
                commandeClientsRepository.save(commande);

        // 6️⃣ Mouvement de stock UNIQUEMENT si on passe à LIVREE
        if (EtatCommande.LIVREE.equals(etatCommande)) {
            updateMvStock(uuidCommande);
        }

        // 7️⃣ Retour d'un DTO de LECTURE
        return CommandeClientMapper.toListDto(savedEntity);
    }

    // Modifier la quantite de la commande
    @Transactional
    @Override
    public CommandeClientListDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        // 1️⃣ Vérifie que la commande existe
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifie que la ligne de commande existe
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Validation métier de la quantité
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Quantité invalide pour la ligne de commande");
            throw new InvalidOperationException(
                    "Impossible de modifier une ligne de commande avec une quantité nulle ou négative",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        // 4️⃣ Vérifie que la commande est modifiable (ex: non LIVRÉE)
        CommandeClients commande = CheckEtatCommande(uuidCommande);

        // 5️⃣ Recherche de la ligne de commande
        LigneCommandeClients ligneCommande = findLigneCommandeClient(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND
                ));

        // 6️⃣ Mise à jour de la quantité
        ligneCommande.setQuantite(quantite);

        // 7️⃣ Sauvegarde de la ligne modifiée
        ligneCommandeClientsRepository.save(ligneCommande);

        // 8️⃣ Retour de la commande (DTO)
        return CommandeClientMapper.toListDto(commande);
    }

    // Modifier un client d'une commande
    @Transactional
    @Override
    public CommandeClientListDto UpdateClient(UUID uuidCommande, UUID uuidClient) {

        // 1️⃣ Vérifier que l'UUID de la commande est fourni
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifier que l'UUID du client est fourni
        if (uuidClient == null) {
            log.error("identifiant du client est null");
            throw new InvalidOperationException(
                    "Impossible de modifier une commande avec identifiant client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        // 3️⃣ Charger la commande EN ENTITY
        // + vérifier qu'elle n'est pas déjà livrée
        CommandeClients commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Charger le nouveau client depuis la base
        Clients client = clientsRepository.findByUuid(uuidClient)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun client trouvé avec l'identifiant " + uuidClient,
                        ErrorCodes.CLIENT_NOT_FOUND
                ));

        // 5️⃣ Mettre à jour UNIQUEMENT le client de la commande
        commande.setClients(client);

        // 6️⃣ Sauvegarder la commande mise à jour
        // JPA fera un UPDATE
        CommandeClients savedCommande =
                commandeClientsRepository.save(commande);

        // 7️⃣ Retourner un DTO de lecture
        return CommandeClientMapper.toListDto(savedCommande);
    }

    @Transactional
    @Override
    public CommandeClientListDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newUuidArticle
    ) {
        // 1️⃣ Vérifie que la commande existe
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifie que la ligne de commande existe
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Vérifie que le nouvel article existe
        CheckUuidArticle(newUuidArticle, "nouvel");

        // 4️⃣ Vérifie que la commande est modifiable (ex: non LIVRÉE)
        CommandeClients commande = CheckEtatCommande(uuidCommande);

        // 5️⃣ Récupération de la ligne de commande
        LigneCommandeClients ligneCommande = findLigneCommandeClient(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND
                ));

        // 6️⃣ Récupération du nouvel article
        Articles article = articleRepository.findByUuid(newUuidArticle)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun article trouvé avec l'identifiant " + newUuidArticle,
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));

        // 7️⃣ Mise à jour de l’article dans la ligne de commande
        ligneCommande.setArticles(article);

        // 8️⃣ Sauvegarde de la ligne modifiée
        ligneCommandeClientsRepository.save(ligneCommande);

        // 9️⃣ Retourne la commande mise à jour
        return CommandeClientMapper.toListDto(commande);
    }

    @Transactional
    @Override
    public CommandeClientListDto DeleteArticle(
            UUID uuidCommande,
            UUID uuidLigneCommande
    ) {

        // 1️⃣ Vérifier que l'UUID de la commande est valide
        CheckUuidCommande(uuidCommande);

        // 2️⃣ Vérifier que l'UUID de la ligne de commande est valide
        CheckUuidLigneCommande(uuidLigneCommande);

        // 3️⃣ Charger la commande EN ENTITY
        // + vérifier qu'elle n'est pas déjà livrée
        CommandeClients commande = CheckEtatCommande(uuidCommande);

        // 4️⃣ Charger la ligne de commande
        LigneCommandeClients ligne = ligneCommandeClientsRepository.findByUuid(uuidLigneCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Ligne de commande introuvable",
                        ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND
                ));

        // 5️⃣ Vérifier que la ligne appartient bien à la commande
        if (!ligne.getCommandeClients().getUuid().equals(uuidCommande)) {
            throw new InvalidOperationException(
                    "La ligne de commande n'appartient pas à cette commande",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        // 6️⃣ Supprimer la ligne de commande
        ligneCommandeClientsRepository.delete(ligne);

        // 7️⃣ Recharger la commande après suppression
        CommandeClients commandeUpdated =
                commandeClientsRepository.findByUuid(uuidCommande)
                        .orElseThrow(() -> new EntityNoFoundException(
                                "Commande client introuvable",
                                ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                        ));

        // 8️⃣ Retourner un DTO de lecture à jour
        return CommandeClientMapper.toListDto(commandeUpdated);
    }


    private void CheckUuidCommande(UUID uuidCommande) {
        if (uuidCommande == null) {
            log.error("identifiant de la commande client est null");
            throw new InvalidOperationException("Impossible de modifier la commande avec identifiant null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void CheckUuidLigneCommande(UUID uuidLigneCommande) {
        if (uuidLigneCommande == null) {
            log.error("identifiant de la ligne de commande est null");
            throw new InvalidOperationException("Impossible de modifier une ligne de commande avec identifiant null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

    }

    private CommandeClients CheckEtatCommande(UUID uuidCommande) {

        CommandeClients commande = commandeClientsRepository.findByUuid(uuidCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Commande client introuvable",
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));

        // Interdiction de modifier une commande déjà livrée
        if (EtatCommande.LIVREE.equals(commande.getEtatCommande())) {
            throw new InvalidOperationException(
                    "Impossible de modifier une commande déjà livrée",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        return commande;
    }

    private Optional<LigneCommandeClients> findLigneCommandeClient(UUID uuidLigneCommande) {
        Optional<LigneCommandeClients> ligneCommandeClientsOptional = ligneCommandeClientsRepository.findByUuid(uuidLigneCommande);
        if (ligneCommandeClientsOptional.isEmpty()) {
            throw new EntityNoFoundException(
                    "Aucune ligne de commande client n'a ete trouve avec identifiant " + uuidLigneCommande
                    , ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientsOptional;
    }

    private void CheckUuidArticle(UUID uuidArticle, String msg) {
        if (uuidArticle == null) {
            log.error("identifiant de " + msg + " est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de commande avec un" + msg + " identifiant article  null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    // methode pour mettre a jour le stock pour Commande clients
    private void updateMvStock(UUID uuidCommande) {
        // 1️⃣ Récupérer la commande
        CommandeClients cmd = commandeClientsRepository.findByUuid(uuidCommande)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Commande introuvable", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
        List<LigneCommandeClients> ligneCommandeClients = ligneCommandeClientsRepository.findCommandeClientsByUuid(uuidCommande);
        ligneCommandeClients.forEach(ligne -> {
            MouvementStocksDto mvtStock = MouvementStocksDto.builder()
                    .article(ligne.getArticles())
                    .typeMouvement(TypeMvtStocks.SORTIE)
                    .sourceMouvement(SourceMvtStocks.COMMANDE_CLIENT)
                    .quantite(ligne.getQuantite())
                    //.idEntreprise(ligne.getIdEntreprise())
                    .dateMvt(Instant.now())
                    .motif("Livraison commande client")
                    .build();
            mvtService.sortieStock(mvtStock); // → APPLIQUE FIFO / LIFO / PMP
        });
    }

}
