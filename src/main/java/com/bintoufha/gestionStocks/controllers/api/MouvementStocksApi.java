package com.bintoufha.gestionStocks.controllers.api;


import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

public interface MouvementStocksApi {

    @GetMapping(APP_ROOT +"mvtStock/stockReel/{uuidArticle}")
    BigDecimal stockReelArticle(@PathVariable("uuidArticle") UUID uuidArticle);

    @GetMapping(APP_ROOT + "mvtStock/filtrer/{uuidArticle}")
    List<MouvementStocksDto> mvtStockArticle(@PathVariable("uuidArticle") UUID uuidArticle);

    @PostMapping(APP_ROOT + "mvtStock/entreeStock")
    MouvementStocksDto entreeStock(@RequestBody MouvementStocksDto mvtStock);

    @PostMapping(APP_ROOT + "mvtStock/sortieStock")
    MouvementStocksDto sortieStock(@RequestBody MouvementStocksDto mvtStock);

    @PostMapping(APP_ROOT + "mvtStock/correctionPositive")
    MouvementStocksDto corectionStockPositive(@RequestBody MouvementStocksDto mvtStockPositive);

    @PostMapping(APP_ROOT + "mvtStock/correctionNegative")
    MouvementStocksDto correctionStockNegative(@RequestBody MouvementStocksDto mvtStockNegation);
}
