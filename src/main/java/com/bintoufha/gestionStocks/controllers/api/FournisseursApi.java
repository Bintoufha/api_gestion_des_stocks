package com.bintoufha.gestionStocks.controllers.api;


import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurListDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Fournisseur", description = "API de gestion des fournisseur")
@RestController
public interface FournisseursApi {

    @PostMapping(
        value=APP_ROOT + "/fournisseur/create",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<FournisseurSaveDto> save(@RequestBody FournisseurSaveDto fournisseursDto);

    @GetMapping(
        value=APP_ROOT + "/fournisseur/recherche/{uuidFournisseur}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<FournisseurListDto> findByUuid(@PathVariable("uuidFournisseur") UUID uuid);

    @GetMapping(
        value=APP_ROOT + "/fournisseur/allFournisseurs",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<List<FournisseurListDto>> findAll();

    @DeleteMapping(
        value=APP_ROOT + "/fournisseur/{uuidFournisseur}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<Void> deleteByUuid(@PathVariable("uuidFournisseur") UUID uuid);
}
