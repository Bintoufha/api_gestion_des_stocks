package com.bintoufha.gestionStocks.controllers.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "LotStocks", description = "API de gestion des Lot des Stocks")
@RestController
@RequestMapping(APP_ROOT + "/lot/stock")
public class LotStocksApi {

//    @PostMapping(value = APP_ROOT
//            + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Enregistrer les lot des stocks", description = "Cette méthode permet d'enregistrer ou de modifier lot des stocks dans la base de données")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Créé ou modifié un article")
//    })

}
