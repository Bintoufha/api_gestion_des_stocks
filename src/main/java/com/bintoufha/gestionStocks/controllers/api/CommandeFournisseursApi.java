package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeFournisseursDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande fournisseurs", description = "API de gestion des commande fournisseur")
@RestController
public interface CommandeFournisseursApi {

    @PostMapping(value = APP_ROOT + "/commandeFournisseur/create")
    ResponseEntity<CommandeFournisseursDto> save(@RequestBody CommandeFournisseursDto commandeFournisseursDto);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/recherche/{uuidCmdFournisseurt}")
    ResponseEntity<CommandeFournisseursDto> findByUUID(@PathVariable UUID uuidCmdFournisseur);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/{reference}")
    ResponseEntity<CommandeFournisseursDto> findByReference(@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/commandeFournisseur/allCmdFournisseur")
    ResponseEntity<List<CommandeFournisseursDto>> findAll();

    @PatchMapping(value = APP_ROOT + "/commandeFournisseur/etat/{uuidCommande}/{etat}")
    ResponseEntity<CommandeFournisseursDto> UpdteEtatCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                                                              @PathVariable("etat")EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "/commandeFournisseur/quantite/{uuidCommande}/{uuidLigneCommande}/{quantite}")
    ResponseEntity<CommandeFournisseursDto> UpdateQuantiteCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                 @PathVariable("uuidLigneCommande") UUID uuidLigneCommande, @PathVariable("quantite")BigDecimal quantite);

    @PatchMapping(value = APP_ROOT + "/commandeFournisseur/fournisseur/{uuidCommande}/{fournisseur}")
    ResponseEntity<CommandeFournisseursDto> UpdateFournisseurs(@PathVariable("uuidCommande")UUID uuidCommande,
                                                               @PathVariable("fournisseur") UUID uuidfournisseur);

    @PatchMapping(value = APP_ROOT + "/commandeFournisseur/article/{uuidCommande}/{uuidLigneCommande}/{uuidArticle}")
    ResponseEntity<CommandeFournisseursDto> UpdateArticle(@PathVariable("uuidCommande") UUID uuidCommande,
               @PathVariable("uuidLigneCommande")UUID uuidLigneCommande, @PathVariable("uuidArticle")UUID newdUuidArticle);

    @DeleteMapping(value = APP_ROOT + "/commandeFournisseur/{uuidCommande}/{uuidLigneCommande}")
    ResponseEntity<CommandeFournisseursDto> DeleteArticle(@PathVariable("uuidCommande") UUID uuidCommande,
                                                          @PathVariable("uuidLigneCommande") UUID uuidLigneCommande);

    @GetMapping(value = APP_ROOT + "/lignecommandeFournisseur/{uuid}")
    List<LigneCommandeFournisseursDto> findAllLigneCommandeFournisseurByUuid(@PathVariable("uuidCommande") UUID uuidCommande);

    @DeleteMapping(value = APP_ROOT + "/commandeFournisseur/supprimer/{uuidCmdFournisseur}")
    ResponseEntity<CommandeFournisseursDto> delete(@PathVariable("uuidCmdFournisseur") UUID uuid);
}
