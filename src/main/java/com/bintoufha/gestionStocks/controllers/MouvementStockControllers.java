package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.MouvementStocksApi;
import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class MouvementStockControllers implements MouvementStocksApi {

    private MouvementStockService service;

    public MouvementStockControllers(MouvementStockService service) {
        this.service = service;
    }

    @Override
    public BigDecimal stockReelArticle(UUID uuidArticle) {
        return service.stockReelArticle(uuidArticle);
    }

    @Override
    public List<MouvementStocksDto> mvtStockArticle(UUID uuidArticle) {
        return service.mvtStockArticle(uuidArticle);
    }

    @Override
    public MouvementStocksDto entreeStock(MouvementStocksDto mvtStock) {
        return service.entreeStock(mvtStock);
    }

    @Override
    public MouvementStocksDto sortieStock(MouvementStocksDto mvtStock) {
        return service.sortieStock(mvtStock);
    }

    @Override
    public MouvementStocksDto corectionStockPositive(MouvementStocksDto mvtStockPositive) {
        return service.corectionStockPositive(mvtStockPositive);
    }

    @Override
    public MouvementStocksDto correctionStockNegative(MouvementStocksDto mvtStockNegation) {
        return service.correctionStockNegative(mvtStockNegation);
    }
}
