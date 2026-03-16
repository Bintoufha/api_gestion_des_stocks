package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.ClientsApi;
import com.bintoufha.gestionStocks.dto.client.ClientListDto;
import com.bintoufha.gestionStocks.dto.client.ClientSaveDto;
import com.bintoufha.gestionStocks.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientControllers implements ClientsApi {

    private ClientService clientService;

    @Autowired
    public ClientControllers(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<ClientSaveDto> save(ClientSaveDto clientsDto) {
        return ResponseEntity.ok(clientService.save(clientsDto));
    }



    @Override
    public ResponseEntity<ClientListDto> findByUUID(UUID uuidClient) {
        return ResponseEntity.ok(clientService.findByUuid(uuidClient));
    }

//    @Override
//    public ResponseEntity<ClientsDto> findByReference(String reference) {
//        return ResponseEntity.ok(commandeClientsService.save(commandeDto));
//    }

    @Override
    public ResponseEntity<List<ClientListDto>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        clientService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
