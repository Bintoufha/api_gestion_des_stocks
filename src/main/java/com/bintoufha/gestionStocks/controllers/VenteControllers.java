package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.VentesApi;
import com.bintoufha.gestionStocks.dto.VentesDto;
import com.bintoufha.gestionStocks.services.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class VenteControllers implements VentesApi {

    private VenteService venteService;

    @Autowired
    public VenteControllers(VenteService venteService) {
        this.venteService = venteService;
    }

    @Override
    public ResponseEntity<VentesDto> save(VentesDto ventesDto) {
        return ResponseEntity.ok(venteService.save(ventesDto));
    }

    @Override
    public ResponseEntity<VentesDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(venteService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<VentesDto> findByReference(String reference) {
        return ResponseEntity.ok(venteService.findByReference(reference));
    }

    @Override
    public ResponseEntity<VentesDto> findAll() {
        return ResponseEntity.ok(venteService.findAll());
    }

    @Override
    public ResponseEntity<VentesDto> deleteByUuid(UUID uuid) {
        venteService.findByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
