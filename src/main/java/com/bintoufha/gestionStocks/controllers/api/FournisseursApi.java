package com.bintoufha.gestionStocks.controllers.api;


import com.bintoufha.gestionStocks.dto.FournisseursDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Fournisseur", description = "API de gestion des fournisseur")
@RestController
public interface FournisseursApi {

    @PostMapping(APP_ROOT + "/fournisseur/create")
    ResponseEntity<FournisseursDto> save(@RequestBody FournisseursDto fournisseursDto);

    @GetMapping(APP_ROOT + "/fournisseur/recherche/{uuidFournisseur}")
    ResponseEntity<FournisseursDto> findByUuid(@PathVariable("uuidFournisseur") UUID uuid);

    @GetMapping(APP_ROOT + "/fournisseur/allFournisseurs")
    ResponseEntity<List<FournisseursDto>> findAll();

    @DeleteMapping(APP_ROOT + "/fournisseur/{uuidFournisseur}")
    ResponseEntity<FournisseursDto> deleteByUuid(@PathVariable("uuidFournisseur") UUID uuid);
}
