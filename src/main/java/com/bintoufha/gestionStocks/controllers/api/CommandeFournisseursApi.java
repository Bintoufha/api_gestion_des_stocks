package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande fournisseurs", description = "API de gestion des commande fournisseur")
@RestController
public interface CommandeFournisseursApi {

    @PostMapping(value = APP_ROOT + "/commandeFournisseur/create")
    ResponseEntity<CommandeFournisseursDto> save (@RequestBody CommandeFournisseursDto commandeFournisseursDto);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/recherche/{uuidCmdFournisseurt}")
    ResponseEntity<CommandeFournisseursDto> findByUUID (@PathVariable UUID uuidCmdFournisseur);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/{reference}")
    ResponseEntity<CommandeFournisseursDto> findByReference (@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/allCmdFournisseur")
    ResponseEntity<List<CommandeFournisseursDto>> findAll ();

    @DeleteMapping(value = APP_ROOT + "/commandeFournisseur/supprimer/{uuidCmdFournisseur}")
    ResponseEntity<CommandeFournisseursDto>delete(@PathVariable("uuidCmdFournisseur") UUID uuid);
}
