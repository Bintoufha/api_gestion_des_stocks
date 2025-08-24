package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Entreprise", description = "API de gestion des entreprises")
@RestController
public interface EntreprisesApi {

    @PostMapping(APP_ROOT + "/entreprises/create")
    ResponseEntity<EntrepriseDto> save(@RequestBody EntrepriseDto entrepriseDto);

    @GetMapping(APP_ROOT + "/entreprises/recherche/{uuidEntreprise}")
    ResponseEntity<EntrepriseDto> findByUuid(@PathVariable("uuidEntreprise") UUID uuid);

    @GetMapping(APP_ROOT + "/entreprises/AllEntreprise")
    ResponseEntity<List<EntrepriseDto>> findAll();

    @DeleteMapping(APP_ROOT + "/entreprises/{uuidEntreprise}")
    ResponseEntity<EntrepriseDto> deleteByUuid(@PathVariable("uuidEntreprise") UUID uuid);
}
