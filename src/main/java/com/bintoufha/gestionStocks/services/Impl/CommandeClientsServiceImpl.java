package com.bintoufha.gestionStocks.services.Impl;

//import com.bintoufha.gestionStocks.dto.ClientsDto;

import com.bintoufha.gestionStocks.dto.*;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import com.bintoufha.gestionStocks.repository.LigneCommandeClientsRepository;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.validator.ArticleValidator;
import com.bintoufha.gestionStocks.validator.CommandClientsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Override
    public CommandeClientsDto save(CommandeClientsDto commandeClientsDto) {

        List<String> errors = CommandClientsValidator.validate(commandeClientsDto);
        if (!errors.isEmpty()) {
            log.warn("la commande de cette client n'est pas valide");
            throw new InvalEntityException("la commande du client n'est pas valide",
                    ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        if (commandeClientsDto.getUuid() != null && commandeClientsDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est " +
                    "est livre", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<Clients> clients = clientsRepository.findByUuid(commandeClientsDto.getClients().getUuid());
        if (clients.isEmpty()) {
            log.warn("client avec identifiant {} n'existe pas dans la base de donne",
                    commandeClientsDto.getClients().getUuid());
            throw new EntityNoFoundException("Aucun client  avec cette identifiant : "
                    + commandeClientsDto.getClients().getUuid() + "n'a ete trouve dans la base",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }
        List<String> ArticleError = new ArrayList<>();
        if (commandeClientsDto.getLigneCommandeClients() != null) {
            commandeClientsDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticle() != null) {
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

        CommandeClients saveCmdClt = commandeClientsRepository.save(CommandeClientsDto.toEntity(commandeClientsDto));

        if (commandeClientsDto.getLigneCommandeClients() != null) {
            commandeClientsDto.getLigneCommandeClients().forEach(ligCmdClt -> {
                LigneCommandeClients ligneCommandeClient = LigneCommandeClientsDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClients(saveCmdClt);
                ligneCommandeClientsRepository.save(ligneCommandeClient);
            });
        }
        return commandeClientsDto.fromEntity(saveCmdClt);
    }

    // chercher un commande client par son UUID
    @Override
    public CommandeClientsDto findByUUID(UUID uuid) {

        if (uuid == null) {
            log.error("identifiant de la commande client est null");
            throw new IllegalArgumentException("L'identifiant UUID ne peut pas être null");
            //return null;
        }
        return commandeClientsRepository.findByUuid(uuid)
                .map(CommandeClientsDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException("Aucune commande client n'a ete trouve avec identifaint : " +
                        uuid, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    // chercher un commande client par son Reference
    @Override
    public CommandeClientsDto findByReference(String reference) {
        if (reference == null) {
            log.error("Reference de la commande client est null");
            throw new IllegalArgumentException("Reference ne peut pas être null");
        }
        return commandeClientsRepository.findByRefernce(reference)
                .map(CommandeClientsDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucune commande client n'a ete trouve avec la refernce : " +
                                reference, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    // recuperer tous les commande client par pour une entreprise donnee
    @Override
    public List<CommandeClientsDto> findAll() {
        return commandeClientsRepository.findAll().stream()
                .map(CommandeClientsDto::fromEntity)
                .collect(Collectors.toList());
    }

    // supprimmer un commande client par son UUID
    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant de la commande client est null");
            throw new IllegalArgumentException("identifiant ne peut pas être null");
        }
        commandeClientsRepository.deleteByUuid(uuid);
    }

    // recuperer tous les ligneCommande client par pour une commande donnee
    @Override
    public List<LigneCommandeClientsDto> findAllLignesCommandeClientsByUuid(UUID uuidCommandeClienst) {
        return ligneCommandeClientsRepository.findAllByUuid(uuidCommandeClienst).stream()
                .map(LigneCommandeClientsDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Modifier l'etat de la commande
    @Override
    public CommandeClientsDto UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande) {
        CheckUuidCommande(uuidCommande);
        if (etatCommande == null || EtatCommande.INCONNU.equals(etatCommande)) {
            log.error("etat de la commande fournisseur est null");
            throw new InvalidOperationException("Impossible de modifier etat de la commande avec etat null ou invalide"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientsDto cmdCltDto = CheckEtatCommande(uuidCommande);
        cmdCltDto.setEtatCommande(etatCommande);

        CommandeClients saveCmdClient = commandeClientsRepository.save(CommandeClientsDto.toEntity(cmdCltDto));
        if (cmdCltDto.isCommandeLivree()){
            updateMvStock(uuidCommande);
        }
       return CommandeClientsDto.fromEntity(saveCmdClient);
    }

    // Modifier la quantite de la commande
    @Override
    public CommandeClientsDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        CheckUuidCommande(uuidCommande);

        CheckUuidLigneCommande(uuidLigneCommande);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("quantite ne peut etre null");
            throw new InvalidOperationException("Impossible de modifier une ligne de commande avec quant la quantite est null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientsDto commandeClients = CheckEtatCommande(uuidCommande);

        Optional<LigneCommandeClients> ligneCommandeClientsOptional = findLigneCommandeClient(uuidLigneCommande);

        LigneCommandeClients ligneCommandeClients = ligneCommandeClientsOptional.get();
        ligneCommandeClients.setQuantite(quantite);
        ligneCommandeClientsRepository.save(ligneCommandeClients);
        return commandeClients;
    }

    // Modifier un client d'une commande
    @Override
    public CommandeClientsDto UpdateClient(UUID uuidCommande, UUID uuidClient) {
        CheckUuidCommande(uuidCommande);
        if (uuidClient == null) {
            log.error("identifiant du client est null");
            throw new InvalidOperationException("Impossible de modifier une commande avec identifiant client null"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientsDto commandeClients = CheckEtatCommande(uuidCommande);
        Optional<Clients> clientsOptional = clientsRepository.findByUuid(uuidClient);
        if (clientsOptional.isEmpty()) {
            throw new EntityNoFoundException("Aucun client n'a été trouver avec identifiant " + uuidClient
                    , ErrorCodes.CLIENT_NOT_FOUND);
        }
        // On injecte le client dans le DTO (conversion Entity -> DTO)
        commandeClients.setClients(ClientsDto.fromEntity(clientsOptional.get()));

        // Conversion DTO -> Entity avant sauvegarde
        CommandeClients entityCommandeClients = CommandeClientsDto.toEntity(commandeClients);

        // Sauvegarde dans la DB
        CommandeClients savedCommandeClients = commandeClientsRepository.save(entityCommandeClients);

        // Conversion Entity -> DTO avant retour
        return CommandeClientsDto.fromEntity(savedCommandeClients);
    }

    @Override
    public CommandeClientsDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        // Vérifie si la commande existe via son UUID
        CheckUuidCommande(uuidCommande);

        // Vérifie si la ligne de commande existe via son UUID
        CheckUuidLigneCommande(uuidLigneCommande);

        // Vérifie si le nouvel article existe via son UUID
        CheckUuidArticle(newdUuidArticle, "nouvel");

        // Vérifie l'état de la commande (par ex. non livrée ou modifiable)
        CommandeClientsDto commandeClients = CheckEtatCommande(uuidCommande);

        // Recherche la ligne de commande concernée par l'UUID
        Optional<LigneCommandeClients> ligneCommandeClients = findLigneCommandeClient(uuidLigneCommande);

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
        LigneCommandeClients ligneCommandeClientToSave = ligneCommandeClients.get();

        // Mise à jour de l’article de la ligne de commande
        ligneCommandeClientToSave.setArticles(articlesOptional.get());

        // Sauvegarde de la ligne de commande mise à jour en base
        ligneCommandeClientsRepository.save(ligneCommandeClientToSave);

        // Retourne la commande (DTO) mise à jour
        return commandeClients;

    }

    @Override
    public CommandeClientsDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {
        // Vérifie si la commande existe via son UUID
        CheckUuidCommande(uuidCommande);

        // Vérifie si la ligne de commande existe via son UUID
        CheckUuidLigneCommande(uuidLigneCommande);
        CommandeClientsDto CommandeClients = CheckEtatCommande(uuidCommande);

        findLigneCommandeClient(uuidLigneCommande);
        ligneCommandeClientsRepository.deleteByUuid(uuidLigneCommande);
        return CommandeClients;
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

    private CommandeClientsDto CheckEtatCommande(UUID uuidCommande) {
        CommandeClientsDto DtoCommandeClients = findByUUID(uuidCommande);
        if (DtoCommandeClients.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier une commande deja livree"
                    , ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return DtoCommandeClients;
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

    private void updateMvStock(UUID uuidCommande ){
        List<LigneCommandeClients>ligneCommandeClients = ligneCommandeClientsRepository.findAllByUuid(uuidCommande);
        ligneCommandeClients.forEach(ligne ->{
            MouvementStocksDto mvtStock = MouvementStocksDto.builder()
                    .article(ligne.getArticles())
                    .typeMouvement(TypeMvtStocks.SORTIE)
                    .sourceMouvement(SourceMvtStocks.COMMANDE_CLIENT)
                    .quantite(ligne.getQuantite())
                    .idEntreprise(ligne.getIdEntreprise())
                    .dateMvt(Instant.now())
                    .build();
            mvtService.sortieStock(mvtStock);
        });
    }

}
