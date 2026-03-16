package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.categorie.CategorieListDto;
import com.bintoufha.gestionStocks.dto.categorie.CategorieSaveDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Categorie", description = "API de gestion des categorie")
public interface CategorieApi {

    @PostMapping(
        value=APP_ROOT +"/categorie/create",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CategorieSaveDto> save (@RequestBody CategorieSaveDto dto);

    @GetMapping(
        value=APP_ROOT +"/categorie/recherche/{uuidCategorie}",
        produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CategorieListDto> findByUuid(@PathVariable("uuidCategorie") UUID uuid);

    @GetMapping(
        value=APP_ROOT +"/categorie/{code}",
        produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CategorieListDto> findByCode (@PathVariable  String code);

    @GetMapping(
        value=APP_ROOT +"/categorie/allCategorie",
        produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<List<CategorieListDto>> findAll();

    @DeleteMapping(
        value=APP_ROOT +"/categorie/{uuidCategorie}",
        produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity delete(@PathVariable("uuidCategorie") UUID uuid);
}
