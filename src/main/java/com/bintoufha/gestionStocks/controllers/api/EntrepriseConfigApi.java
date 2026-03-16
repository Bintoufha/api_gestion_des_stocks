package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigSaveDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Entreprise Configuration", description = "API de gestion des Entreprise Configuration")
@RestController
public interface EntrepriseConfigApi {

    @PostMapping(
            value=APP_ROOT +"/entrepriseConfig/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("@securityService.isMyBoutique(#configDto.boutiqueId) and hasPermission(#boutiqueId, 'PRICE_CONFIGURE')")
    ResponseEntity<EntrepriseConfigSaveDto> save (@RequestBody EntrepriseConfigSaveDto configDto);

    @GetMapping(
            value=APP_ROOT +"/entrepriseConfig/recherche/{uuidEntrepriseConfig}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<EntrepriseConfigListDto> findByUuid(@PathVariable("uuidEntrepriseConfig") UUID uuid);

    @GetMapping(
            value=APP_ROOT +"/entrepriseConfig/allConfig",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<List<EntrepriseConfigListDto>> findAll();



    @GetMapping("/boutique/{boutiqueId}")
    @PreAuthorize("@securityService.isMyBoutique(#boutiqueId) and hasPermission(#boutiqueId, 'PRICE_READ')")
    public ResponseEntity<List<EntrepriseConfigListDto>> getTarificationsByBoutique(UUID entrepriseUuid);


    @GetMapping("/boutique/{boutiqueId}/categorie/{categorieId}")
    @PreAuthorize("@securityService.isMyBoutique(#boutiqueId) and hasPermission(#boutiqueId, 'PRICE_READ')")
    public ResponseEntity<EntrepriseConfigListDto> getTarificationByBoutiqueAndCategorie(
            @PathVariable UUID entrepriseUuid,
            @PathVariable UUID categorieUuid);

    @PostMapping("/boutique/{boutiqueId}/categorie/{categorieId}/calcul")
    @PreAuthorize("@securityService.isMyBoutique(#boutiqueId) and hasPermission(#boutiqueId, 'PRICE_READ')")
    public ResponseEntity<BigDecimal> calculerPrix(
            @PathVariable UUID entrepriseUuid,
            @PathVariable UUID categorieUuid,
            @RequestParam BigDecimal prixAchat,
            @RequestParam String typePrix);

}
