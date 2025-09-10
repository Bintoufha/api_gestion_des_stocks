package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.MouvementStocksDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MouvementStockService {

    BigDecimal stockReelArticle(UUID uuidArticle);

    List<MouvementStocksDto> mvtStockArticle( UUID uuidArticle);

    MouvementStocksDto entreeStock(MouvementStocksDto mvtStock);

    MouvementStocksDto sortieStock(MouvementStocksDto mvtStock);

    MouvementStocksDto corectionStockPositive (MouvementStocksDto mvtStockPositive);

    MouvementStocksDto correctionStockNegative (MouvementStocksDto mvtStockNegation);

}
