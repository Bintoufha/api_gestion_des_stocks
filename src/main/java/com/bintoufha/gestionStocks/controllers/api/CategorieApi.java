package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Categorie", description = "API de gestion des categorie")
public interface CategorieApi {

    @PostMapping(APP_ROOT +"/categorie/create")
    ResponseEntity<CategoriesDto> save (@RequestBody CategoriesDto dto);

    @GetMapping(APP_ROOT +"/categorie/recherche/{uuidCategorie}")
    ResponseEntity<CategoriesDto> findByUuid(@PathVariable("uuidCategorie") UUID uuid);

    @GetMapping(APP_ROOT +"/categorie/{code}")
    ResponseEntity<CategoriesDto> findByCode (@PathVariable  String code);

    @GetMapping(APP_ROOT +"/categorie/allCategorie")
    ResponseEntity<List<CategoriesDto>> findAll();

    @DeleteMapping(APP_ROOT +"/categorie/{uuidCategorie}")
    ResponseEntity delete(@PathVariable("uuidCategorie") UUID uuid);
}
