package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Client", description = "API de gestion des clients")
@RestController
public interface ClientsApi {


    @PostMapping(value = APP_ROOT + "/clients/create")
    ResponseEntity<ClientsDto> save(@RequestBody ClientsDto clientsDto);

    @GetMapping(value = APP_ROOT + "/clients/recherche/{uuidClient}")
    ResponseEntity<ClientsDto> findByUUID(@PathVariable UUID uuidCmdClient);

//    @GetMapping(value = APP_ROOT + "/commandeClients/{reference}")
//    ResponseEntity<ClientsDto> findByReference(@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/clients/allClt")
    ResponseEntity<List<ClientsDto>> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/supprimer/{uuidClient}")
    ResponseEntity<ClientsDto> delete(@PathVariable("uuidCmdClient") UUID uuid);
}
