package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.EntrepriseConfigApi;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigSaveDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.services.EntrepriseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class EntrepriseConfigControllers implements EntrepriseConfigApi {

    private final EntrepriseConfigService entrepriseConfigService;

    @Autowired
    public EntrepriseConfigControllers(EntrepriseConfigService entrepriseConfigService) {
        this.entrepriseConfigService = entrepriseConfigService;
    }

    @Override
    public ResponseEntity<EntrepriseConfigSaveDto> save(EntrepriseConfigSaveDto configDto) {

        return ResponseEntity.ok(entrepriseConfigService.save(configDto));
    }

    @Override
    public ResponseEntity<EntrepriseConfigListDto> findByUuid(UUID uuid) {

        return ResponseEntity.ok(entrepriseConfigService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<EntrepriseConfigListDto>> findAll() {

        return ResponseEntity.ok(entrepriseConfigService.findAll());
    }

    @Override
    public ResponseEntity<List<EntrepriseConfigListDto>> getTarificationsByBoutique(UUID entrepriseUuid) {
        List<EntrepriseConfigListDto> tarifications = entrepriseConfigService.getTarificationsByBoutique(entrepriseUuid);
        return ResponseEntity.ok(tarifications);
    }

    @Override
    public ResponseEntity<EntrepriseConfigListDto> getTarificationByBoutiqueAndCategorie(UUID entrepriseUuid, UUID categorieUuid) {
        return ResponseEntity.ok(entrepriseConfigService.getTarificationByBoutiqueAndCategorie(entrepriseUuid,categorieUuid));
    }

    @Override
    public ResponseEntity<BigDecimal> calculerPrix(UUID entrepriseUuid, UUID categorieUuid, BigDecimal prixAchat, String typePrix) {
        return ResponseEntity.ok(entrepriseConfigService.calculerPrix(
                entrepriseUuid,
                categorieUuid,
                prixAchat,
                typePrix));
    }
}
