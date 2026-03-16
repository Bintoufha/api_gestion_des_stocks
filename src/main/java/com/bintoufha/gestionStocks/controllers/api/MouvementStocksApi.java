package com.bintoufha.gestionStocks.controllers.api;


import com.bintoufha.gestionStocks.dto.mouvement.MouvementStocksDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

public interface MouvementStocksApi {

    @GetMapping(
        value=APP_ROOT +"mvtStock/stockReel/{uuidArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    BigDecimal stockReelArticle(@PathVariable("uuidArticle") UUID uuidArticle);

    @GetMapping(
        value=APP_ROOT + "mvtStock/filtrer/{uuidArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    List<MouvementStocksDto> mvtStockArticle(@PathVariable("uuidArticle") UUID uuidArticle);

    @PostMapping(
        value=APP_ROOT + "mvtStock/entreeStock",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    MouvementStocksDto entreeStock(@RequestBody MouvementStocksDto mvtStock);

    @PostMapping(
        value=APP_ROOT + "mvtStock/sortieStock",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    MouvementStocksDto sortieStock(@RequestBody MouvementStocksDto mvtStock);

    @PostMapping(
        value=APP_ROOT + "mvtStock/correctionPositive",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    MouvementStocksDto corectionStockPositive(@RequestBody MouvementStocksDto mvtStockPositive);

    @PostMapping(
        value=APP_ROOT + "mvtStock/correctionNegative",
consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
    )
    MouvementStocksDto correctionStockNegative(@RequestBody MouvementStocksDto mvtStockNegation);
}
