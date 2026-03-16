package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.lot.LotStockSaveDto;
import com.bintoufha.gestionStocks.model.LotStocks;

public class LotMapper {

    public static LotStockSaveDto fromEntity(LotStocks lotStocks){
        if (lotStocks == null){
            return  null;
        }
        return LotStockSaveDto.builder()
                .uuid(lotStocks.getUuid())
                .idEntreprise(lotStocks.getIdEntreprise())
                .qteCommande(lotStocks.getQteCommande())
                .qteRestant(lotStocks.getQteRestant())
                .prixUnitaire(lotStocks.getPrixUnitaire())
                .articles(lotStocks.getArticles())
                .build();
    }
    public static LotStocks toEntity(LotStockSaveDto lotStocksDto){
        if (lotStocksDto == null){
            return  null;
        }
        LotStocks lotStocks = new LotStocks();
        lotStocks.setUuid(lotStocksDto.getUuid());
        lotStocks.setIdEntreprise(lotStocksDto.getIdEntreprise());
        lotStocks.setQteCommande(lotStocksDto.getQteCommande());
        lotStocks.setQteRestant(lotStocksDto.getQteRestant());
        lotStocks.setPrixUnitaire(lotStocksDto.getPrixUnitaire());
        lotStocks.setArticles(lotStocksDto.getArticles());
        return lotStocks;
    }
}
