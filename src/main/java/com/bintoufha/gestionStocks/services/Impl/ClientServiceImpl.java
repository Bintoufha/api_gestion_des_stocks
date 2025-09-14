package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import com.bintoufha.gestionStocks.services.ClientService;
import com.bintoufha.gestionStocks.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final DefaultErrorAttributes errorAttributes;
    private ClientsRepository clientsRepository;
    private CommandeClientsRepository commandeClientsRepository;

    public ClientServiceImpl(DefaultErrorAttributes errorAttributes, ClientsRepository clientsRepository, CommandeClientsRepository commandeClientsRepository) {
        this.clientsRepository = clientsRepository;
        this.errorAttributes = errorAttributes;
        this.commandeClientsRepository = commandeClientsRepository;
    }

    @Override
    public ClientsDto save(ClientsDto clientsDto) {
        List<String> errors = ClientValidator.validate(clientsDto);
        if (!errors.isEmpty()) {
            log.error("donnee non valide");
            throw new InvalEntityException("tout les information du client n'ont pas ete renseigner",
                    ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientsDto.fromEntity(
                clientsRepository.save(ClientsDto.toEntity(clientsDto))
        );
    }

    @Override
    public ClientsDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant client est introuvable");
        }

        Optional<Clients> clients = clientsRepository.findByUuid(uuid);

        return clients
                .map(ClientsDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "le client avec identifiant : " + uuid + "n'existe pas dans la base de donnee",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<ClientsDto> findAll() {
        return clientsRepository.findAll().stream()
                .map(ClientsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if (uuid == null){
            log.error("identifiants invalide");
        }
        List<CommandeClients> commandeClients =commandeClientsRepository.findAllByClientsUuid(uuid);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un client qui a deja des commande clients",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientsRepository.findByUuid(uuid);
    }
}
