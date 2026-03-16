package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.client.ClientListDto;
import com.bintoufha.gestionStocks.dto.client.ClientSaveDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Client", description = "API de gestion des clients")
@RestController
public interface ClientsApi {


    @PostMapping(
            value = APP_ROOT + "/clients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<ClientSaveDto> save(@RequestBody ClientSaveDto clientsDto);

    @GetMapping(
            value = APP_ROOT + "/clients/recherche/{uuidClient}",
                produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<ClientListDto> findByUUID(@PathVariable("uuidClient") UUID uuid);

    @GetMapping(
            value = APP_ROOT + "/clients/allClt",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<List<ClientListDto>> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/{uuidClient}")
    ResponseEntity<Void> delete(@PathVariable("uuidClient") UUID uuid);
}
