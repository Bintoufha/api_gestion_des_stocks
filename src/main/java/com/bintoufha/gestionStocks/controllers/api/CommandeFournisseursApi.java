package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande fournisseurs", description = "API de gestion des commande fournisseur")
@RestController
public interface CommandeFournisseursApi {

    @PostMapping(
      value = APP_ROOT + "/commandeFournisseur/create",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseurSaveDto> save(@RequestBody CommandeFournisseurSaveDto commandeFournisseursDto);

    @GetMapping(
      value = APP_ROOT + "/commandeFournisseur/recherche/{uuidCmdFournisseurt}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> findByUUID(@PathVariable UUID uuidCmdFournisseur);

    @GetMapping(
      value = APP_ROOT + "/commandeFournisseur/{reference}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> findByReference(@PathVariable String reference);

    @GetMapping(
      value = APP_ROOT + "/commandeFournisseur/allCmdFournisseur",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<List<CommandeFournisseursListDto>> findAll();

    @PatchMapping(
      value = APP_ROOT + "/commandeFournisseur/etat/{uuidCommande}/{etat}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> UpdteEtatCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                                                              @PathVariable("etat")EtatCommande etatCommande);

    @PatchMapping(
      value = APP_ROOT + "/commandeFournisseur/quantite/{uuidCommande}/{uuidLigneCommande}/{quantite}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> UpdateQuantiteCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                 @PathVariable("uuidLigneCommande") UUID uuidLigneCommande, @PathVariable("quantite")BigDecimal quantite);

    @PatchMapping(
      value = APP_ROOT + "/commandeFournisseur/fournisseur/{uuidCommande}/{fournisseur}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> UpdateFournisseurs(@PathVariable("uuidCommande")UUID uuidCommande,
                                                               @PathVariable("fournisseur") UUID uuidfournisseur);

    @PatchMapping(
      value = APP_ROOT + "/commandeFournisseur/article/{uuidCommande}/{uuidLigneCommande}/{uuidArticle}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> UpdateArticle(@PathVariable("uuidCommande") UUID uuidCommande,
               @PathVariable("uuidLigneCommande")UUID uuidLigneCommande, @PathVariable("uuidArticle")UUID newdUuidArticle);

    @DeleteMapping(
      value = APP_ROOT + "/commandeFournisseur/{uuidCommande}/{uuidLigneCommande}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> DeleteArticle(@PathVariable("uuidCommande") UUID uuidCommande,
                                                          @PathVariable("uuidLigneCommande") UUID uuidLigneCommande);

//    @GetMapping(
//      value = APP_ROOT + "/lignecommandeFournisseur/{uuid}",
//            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
//    )
//    List<CommandeFournisseursListDto> findAllLigneCommandeFournisseurByUuid(@PathVariable("uuidCommande") UUID uuidCommande);

    @DeleteMapping(
      value = APP_ROOT + "/commandeFournisseur/supprimer/{uuidCmdFournisseur}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    ResponseEntity<CommandeFournisseursListDto> delete(@PathVariable("uuidCmdFournisseur") UUID uuid);
}
