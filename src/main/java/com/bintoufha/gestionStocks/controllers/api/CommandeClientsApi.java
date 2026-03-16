package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientListDto;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Commande client", description = "API de gestion des commande clients")
@RestController
//@RequestMapping(APP_ROOT + "/commandeClients")
public interface CommandeClientsApi {

    @PostMapping(
            value = APP_ROOT + "/commandeClients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientSaveDto> save (@RequestBody CommandeClientSaveDto commandeDto);

    @PatchMapping(
            value = APP_ROOT + "/commande/update/etat/{uuidCommande}/{etatCommande}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto> UpdteCommandeClients(
            @PathVariable("uuidCommande") UUID uuidCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(
        value = APP_ROOT + "/commande/update/quantite/{uuidCommande}/{uuidLigneCommande}/{quantite}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto>UpdateQuantiteCommande(@PathVariable("uuidCommande") UUID uuidCommande,
                    @PathVariable("uuidLigneCommande") UUID uuidLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = APP_ROOT + "/commande/update/client/{uuidCommande}/{uuidClient}")
    ResponseEntity<CommandeClientListDto>UpdateClient (@PathVariable("uuidCommande") UUID uuidCommande,
                                                    @PathVariable("uuidClient")UUID uuidClient);

    @PatchMapping(value = APP_ROOT + "/commande/update/article/{uuidCommande}/{uuidLigneCommande}/{UuidArticle}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto>UpdateArticle(@PathVariable("uuidCommande")UUID uuidCommande,
         @PathVariable("uuidLigneCommande")UUID uuidLigneCommande, @PathVariable("UuidArticle")UUID newdUuidArticle);

    @GetMapping(value = APP_ROOT + "/commandeClients/recherche/{uuidCmdClient}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto> findByUUID (@PathVariable UUID uuidCmdClient);

    @GetMapping(value = APP_ROOT + "/commandeClients/{reference}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto> findByReference (@PathVariable String reference);

    @GetMapping(value = APP_ROOT + "/commandeClients/ligneCommande/{uuidCommande}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<List<LigneCommandeClientSaveDto>> findAllLignesCommandeClientsByUuid(@PathVariable("uuidCommande") UUID uuidCommandeClients);

    @GetMapping(value = APP_ROOT + "/commandeClients/allCmdClient",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<List<CommandeClientListDto>> findAll ();

    @DeleteMapping(value = APP_ROOT + "/commandeClients/delete/article/{uuidCommande}/{uuidLigneCommande}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<CommandeClientListDto> DeleteArticle(@PathVariable("uuidCommande") UUID uuidCommande,
                                                    @PathVariable("uuidLigneCommande")UUID uuidLigneCommande);

    @DeleteMapping(value = APP_ROOT + "/commandeClients/supprimer/{uuidCmdClient}",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    ResponseEntity<Void>delete(@PathVariable("uuidCmdClient") UUID uuid);
}
