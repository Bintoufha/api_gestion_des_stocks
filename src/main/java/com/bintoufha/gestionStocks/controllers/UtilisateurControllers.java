package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.UtilisateursApi;
import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UtilisateurControllers implements UtilisateursApi {

    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurControllers(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public ResponseEntity<UtilisateursDto> findByEmail(String email) {
        return ResponseEntity.ok(utilisateurService.findByEmail(email));
    }

    @Override
    public ResponseEntity<UtilisateursDto> save(UtilisateursDto utilisateursDto) {
        return ResponseEntity.ok(utilisateurService.save(utilisateursDto));
    }

    @Override
    public ResponseEntity<UtilisateursDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(utilisateurService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<UtilisateursDto>> findAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    @Override
    public ResponseEntity<UtilisateursDto> deleteByUuid(UUID uuid) {
        utilisateurService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
