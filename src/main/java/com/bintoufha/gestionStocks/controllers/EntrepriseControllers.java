package com.bintoufha.gestionStocks.controllers;


import com.bintoufha.gestionStocks.controllers.api.EntreprisesApi;
import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import com.bintoufha.gestionStocks.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class EntrepriseControllers implements EntreprisesApi {

    private EntrepriseService entrepriseService;


    @Autowired
    public EntrepriseControllers(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Override
    public ResponseEntity<EntrepriseDto> save(EntrepriseDto entrepriseDto) {
        return ResponseEntity.ok(entrepriseService.save(entrepriseDto));
    }

    @Override
    public ResponseEntity<EntrepriseDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(entrepriseService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<EntrepriseDto>> findAll() {
        return ResponseEntity.ok(entrepriseService.findAll());
    }

    @Override
    public ResponseEntity<EntrepriseDto> deleteByUuid(UUID uuid) {
        entrepriseService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
