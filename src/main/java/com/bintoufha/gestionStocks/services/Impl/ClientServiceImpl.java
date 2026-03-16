package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.client.ClientListDto;
import com.bintoufha.gestionStocks.dto.client.ClientSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.ClientMapper;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.repository.CommandeClientsRepository;
import org.springframework.transaction.annotation.Transactional;
import com.bintoufha.gestionStocks.services.ClientService;
import com.bintoufha.gestionStocks.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
    public ClientSaveDto save(ClientSaveDto clientsDto) {
        List<String> errors = ClientValidator.validate(clientsDto);
        if (!errors.isEmpty()) {
           // log.error("donnee non valide");
            throw new InvalEntityException("tout les information du client n'ont pas ete renseigner",
                    ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        // 3️⃣ Mapper DTO → Entity
        Clients clients = ClientMapper.toEntity(clientsDto);

        // 4️⃣ Sauvegarder
        Clients saved = clientsRepository.save(clients);

        // 5️⃣ Retourner DTO propre
        return ClientMapper.toSaveDto(saved);
    }

    @Override
    public ClientListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant article est introuvable");
            throw new IllegalArgumentException("UUID de l'article ne peut pas être null");
        }

        // Récupération du client ou exception si non trouvé
        Clients clients = clientsRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun clients avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return ClientMapper.toListDto(clients);
    }

    @Override
    public List<ClientListDto> findAll() {
        return clientsRepository.findAll()
                .stream()
                .map(client -> {
                    // ou via TarificationService si besoin
                    return ClientMapper.toListDto(client);
                })
                .toList();
    }

   @Override
   @Transactional
    public void deleteByUuid(UUID uuid) {

        if (uuid == null) {
            log.error("Identifiant invalide");
            throw new EntityNoFoundException(
                    "L'UUID fourni est null",
                    ErrorCodes.CLIENT_NOT_VALID
            );
        }

        // Vérifier si le client existe
        Clients client = clientsRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Client avec UUID " + uuid + " non trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));

        // Vérifier si le client a des commandes
        List<CommandeClients> commandeClients = commandeClientsRepository.findAllByClientsUuid(uuid);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer un client qui a déjà des commandes",
                    ErrorCodes.CLIENT_ALREADY_IN_USE
            );
        }

        // Supprimer le client (CORRECT)
        clientsRepository.delete(client);
    }


}
