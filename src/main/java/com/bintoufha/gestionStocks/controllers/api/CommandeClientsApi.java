package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande client", description = "API de gestion des commande clients")
@RestController
//@RequestMapping(APP_ROOT + "/commandeClients")
public interface CommandeClientsApi {

    @PostMapping(value = APP_ROOT + "/commandeClients/create")
    ResponseEntity<CommandeClientsDto> save (@RequestBody CommandeClientsDto commandeDto);

    @GetMapping(value = APP_ROOT + "/commandeClients/recherche/{uuidCmdClient}")
    ResponseEntity<CommandeClientsDto> findByUUID (@PathVariable UUID uuidCmdClient);

    @GetMapping(value = APP_ROOT + "/commandeClients/{reference}")
    ResponseEntity<CommandeClientsDto> findByReference (@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/commandeClients/allCmdClient")
    ResponseEntity<List<CommandeClientsDto>> findAll ();

    @DeleteMapping(value = APP_ROOT + "/commandeClients/supprimer/{uuidCmdClient}")
    ResponseEntity<CommandeClientsDto>delete(@PathVariable("uuidCmdClient") UUID uuid);
}
