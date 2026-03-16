package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.FournisseursApi;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurListDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;
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
    public ResponseEntity<FournisseurSaveDto> save(FournisseurSaveDto fournisseursDto) {
        return ResponseEntity.ok(fournisseurService.save(fournisseursDto));
    }

    @Override
    public ResponseEntity<FournisseurListDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(fournisseurService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<FournisseurListDto>> findAll() {
        return ResponseEntity.ok(fournisseurService.findAll());
    }

    @Override
    public ResponseEntity<Void> deleteByUuid(UUID uuid) {
        fournisseurService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
