package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande client", description = "API de gestion des commande clients")
@RestController
//@RequestMapping(APP_ROOT + "/commandeClients")
public interface CommandeClientsApi {

    @PostMapping(value = APP_ROOT + "/commandeClients/create")
    ResponseEntity<CommandeClientsDto> save (@RequestBody CommandeClientsDto commandeDto);

    @PatchMapping(value = APP_ROOT + "/commande/update/etat/{uuidCommande}/{etatCommande}")
    ResponseEntity<CommandeClientsDto> UpdteCommandeClients(
            @PathVariable("uuidCommande") UUID uuidCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "/commande/update/quantite/{uuidCommande}/{uuidLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientsDto>UpdateQuantiteCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                    @PathVariable("uuidLigneCommande") UUID uuidLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = APP_ROOT + "/commande/update/client/{uuidCommande}/{uuidClient}")
    ResponseEntity<CommandeClientsDto>UpdateClient (@PathVariable("uuidCommande") UUID uuidCommande,
                                                    @PathVariable("uuidClient")UUID uuidClient);

    @PatchMapping(value = APP_ROOT + "/commande/update/article/{uuidCommande}/{uuidLigneCommande}/{UuidArticle}")
    ResponseEntity<CommandeClientsDto>UpdateArticle(@PathVariable("uuidCommande")UUID uuidCommande,
         @PathVariable("uuidLigneCommande")UUID uuidLigneCommande, @PathVariable("UuidArticle")UUID newdUuidArticle);

    @GetMapping(value = APP_ROOT + "/commandeClients/recherche/{uuidCmdClient}")
    ResponseEntity<CommandeClientsDto> findByUUID (@PathVariable UUID uuidCmdClient);

    @GetMapping(value = APP_ROOT + "/commandeClients/{reference}")
    ResponseEntity<CommandeClientsDto> findByReference (@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/commandeClients/ligneCommande/{uuidCommande}")
    ResponseEntity<List<LigneCommandeClientsDto>> findAllLignesCommandeClientsByUuid(@PathVariable("uuidCommande") UUID uuidCommandeClients);

    @GetMapping(value = APP_ROOT + "/commandeClients/allCmdClient")
    ResponseEntity<List<CommandeClientsDto>> findAll ();

    @DeleteMapping(value = APP_ROOT + "/commandeClients/delete/article/{uuidCommande}/{uuidLigneCommande}")
    ResponseEntity<CommandeClientsDto> DeleteArticle(@PathVariable("uuidCommande") UUID uuidCommande,
                                                    @PathVariable("uuidLigneCommande")UUID uuidLigneCommande);

    @DeleteMapping(value = APP_ROOT + "/commandeClients/supprimer/{uuidCmdClient}")
    ResponseEntity<CommandeClientsDto>delete(@PathVariable("uuidCmdClient") UUID uuid);
}
