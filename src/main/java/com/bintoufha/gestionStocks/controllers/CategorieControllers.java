package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.CategorieApi;
import com.bintoufha.gestionStocks.dto.categorie.CategorieListDto;
import com.bintoufha.gestionStocks.dto.categorie.CategorieSaveDto;
import com.bintoufha.gestionStocks.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CategorieControllers implements CategorieApi {

    private final CategorieService categorieService;

    @Autowired
    public CategorieControllers(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public ResponseEntity<CategorieSaveDto> save(CategorieSaveDto dto) {
        return ResponseEntity.ok(categorieService.save(dto));
    }

    @Override
    public ResponseEntity<CategorieListDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(categorieService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<CategorieListDto> findByCode(String code) {
        return ResponseEntity.ok(categorieService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CategorieListDto>> findAll() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @Override
    public ResponseEntity delete(UUID uuid) {
        categorieService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
