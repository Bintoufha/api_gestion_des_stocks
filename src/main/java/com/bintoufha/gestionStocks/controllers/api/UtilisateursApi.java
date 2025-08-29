package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
@RestController
public interface UtilisateursApi {

    @PostMapping(APP_ROOT + "/utilisateurs/create_utilisateur")
    ResponseEntity<UtilisateursDto> save(@RequestBody UtilisateursDto utilisateursDto);

    @GetMapping(APP_ROOT + "/utilisateurs/recherche/uuid_utilisateur")
    ResponseEntity<UtilisateursDto> findByUuid(@PathVariable("uuid_utilisateur") UUID uuid);

    @GetMapping(APP_ROOT + "/utilisateurs/email/{email}")
    ResponseEntity<UtilisateursDto> findByEmail(@PathVariable("email") String email);

    @GetMapping(APP_ROOT + "/utilisateurs/All_utilisateur")
    ResponseEntity<List<UtilisateursDto>>findAll();

    @DeleteMapping(APP_ROOT + "/utilisateurs/uuid_utilisateur")
    ResponseEntity<UtilisateursDto> deleteByUuid(@PathVariable("uuid_utilisateur") UUID uuid);
}
