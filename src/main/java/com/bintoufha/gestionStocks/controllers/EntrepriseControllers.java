package com.bintoufha.gestionStocks.controllers;


import com.bintoufha.gestionStocks.controllers.api.EntreprisesApi;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.StatutEntreprise;
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
    public ResponseEntity<EntrepriseSaveDto> save(EntrepriseSaveDto entrepriseDto) {
        return ResponseEntity.ok(entrepriseService.save(entrepriseDto));
    }

    @Override
    public ResponseEntity<EntrepriseListDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(entrepriseService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<EntrepriseListDto>> findAll() {
        return ResponseEntity.ok(entrepriseService.findAll());
    }

    @Override
    public ResponseEntity<List<EntrepriseListDto>> getEntrepriseByTypeEntreprises(UUID typeUuid) {
        List<EntrepriseListDto> boutiques = entrepriseService.getEntrepriseByTypeEntreprises(typeUuid);
        return ResponseEntity.ok(boutiques);
    }

    @Override
    public ResponseEntity<List<EntrepriseListDto>> getEntrepriseByVille(String ville) {
        List<EntrepriseListDto> ville1 = entrepriseService.getEntrepriseByVille(ville);
        return ResponseEntity.ok(ville1);
    }

    @Override
    public ResponseEntity<List<EntrepriseListDto>> getEntrepriseByStatut(StatutEntreprise statut) {
        List<EntrepriseListDto> statut1 = entrepriseService.getEntrepriseByStatut(statut);
        return ResponseEntity.ok(statut1);
    }

    @Override
    public ResponseEntity<Void> deleteByUuid(UUID uuid) {
        entrepriseService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
