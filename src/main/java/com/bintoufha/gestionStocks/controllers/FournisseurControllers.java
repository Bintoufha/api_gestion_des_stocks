package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.FournisseursApi;
import com.bintoufha.gestionStocks.dto.FournisseursDto;
import com.bintoufha.gestionStocks.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class FournisseurControllers implements FournisseursApi {

    private FournisseurService fournisseurService;

    @Autowired
    public FournisseurControllers(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @Override
    public ResponseEntity<FournisseursDto> save(FournisseursDto fournisseursDto) {
        return ResponseEntity.ok(fournisseurService.save(fournisseursDto));
    }

    @Override
    public ResponseEntity<FournisseursDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(fournisseurService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<FournisseursDto>> findAll() {
        return ResponseEntity.ok(fournisseurService.findAll());
    }

    @Override
    public ResponseEntity<FournisseursDto> deleteByUuid(UUID uuid) {
        fournisseurService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
