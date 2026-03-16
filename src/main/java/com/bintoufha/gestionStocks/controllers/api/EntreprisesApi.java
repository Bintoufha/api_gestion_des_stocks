package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.StatutEntreprise;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Entreprise", description = "API de gestion des entreprises")
@RestController
public interface EntreprisesApi {

    @PostMapping(
            value = APP_ROOT + "/entreprises/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    ResponseEntity<EntrepriseSaveDto> save(@RequestBody EntrepriseSaveDto entrepriseDto);

    @GetMapping(
            value = APP_ROOT + "/entreprises/recherche/{uuidEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL') or @securityService.isMyBoutique(#id)")
    ResponseEntity<EntrepriseListDto> findByUuid(@PathVariable("uuidEntreprise") UUID uuid);

    @GetMapping(
            value = APP_ROOT + "/entreprises/AllEntreprise",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<List<EntrepriseListDto>> findAll();

    @GetMapping(
            value = APP_ROOT + "/entreprises/type_entreprise/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<List<EntrepriseListDto>> getEntrepriseByTypeEntreprises(@PathVariable("type") UUID typeUuid);

    @GetMapping(
            value = APP_ROOT + "/entreprises/entreprise_par_ville/{ville}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<List<EntrepriseListDto>> getEntrepriseByVille(@PathVariable("ville") String ville);

    @GetMapping(
            value = APP_ROOT + "/entreprises/status_entreprise/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<List<EntrepriseListDto>> getEntrepriseByStatut(@PathVariable("status") StatutEntreprise statut);

    @DeleteMapping(
            value = APP_ROOT + "/entreprises/{uuidEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<Void> deleteByUuid(@PathVariable("uuidEntreprise") UUID uuid);
}
