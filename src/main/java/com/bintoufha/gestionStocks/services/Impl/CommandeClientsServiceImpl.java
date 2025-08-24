package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import com.bintoufha.gestionStocks.repository.LigneCommandeClientsRepository;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import com.bintoufha.gestionStocks.validator.CommandClientsValidator;
//import com.bintoufha.gestionStocks.model.LigneCommandeClients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public CommandeClientsServiceImpl(
            CommandeClientsRepository commandeClientsRepository,
            ClientsRepository clientsRepository,
            ArticleRepository articleRepository, LigneCommandeClientsRepository ligneCommandeClientsRepository) {
        this.commandeClientsRepository = commandeClientsRepository;
        this.clientsRepository = clientsRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientsRepository = ligneCommandeClientsRepository;
    }

    @Override
    public CommandeClientsDto save(CommandeClientsDto commandeClientsDto) {

        List<String> errors = CommandClientsValidator.validate(commandeClientsDto);
        if (!errors.isEmpty()) {
            log.warn("la commande de cette client n'est pas valide");
            throw new InvalEntityException("la commande du client n'est pas valide",
                    ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
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

    @Override
    public List<CommandeClientsDto> findAll() {
        return commandeClientsRepository.findAll().stream()
                .map(CommandeClientsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant de la commande client est null");
            throw new IllegalArgumentException("identifiant ne peut pas être null");
        }
        commandeClientsRepository.deleteByUuid(uuid);
    }
}
