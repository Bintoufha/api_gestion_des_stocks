package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.mouvement.MouvementStocksDto;
import com.bintoufha.gestionStocks.model.MouvementStocks;

public class MouvementMapper {

    public static MouvementStocksDto fromEntity (MouvementStocks mouvementStocks){
        if (mouvementStocks == null){
            return null;
        }
        return MouvementStocksDto.builder()
                .uuid(mouvementStocks.getUuid())
                .article(mouvementStocks.getArticles())
                .typeMouvement(mouvementStocks.getTypeMouvement())
                .quantite(mouvementStocks.getQuantite())
                .motif(mouvementStocks.getMotif())
                .dateMvt(mouvementStocks.getDateMvt())
                .sourceMouvement(mouvementStocks.getSourceMouvement())
                .idEntreprise(mouvementStocks.getIdEntreprise())
                .build();
    }
    public static MouvementStocks toEntity (MouvementStocksDto mouvementStocksDto){
        if (mouvementStocksDto == null){
            return null;
        }
        MouvementStocks mouvementStocks = new MouvementStocks();
        mouvementStocks.setUuid(mouvementStocksDto.getUuid());
        mouvementStocks.setArticles(mouvementStocksDto.getArticle());
        mouvementStocks.setIdEntreprise(mouvementStocksDto.getIdEntreprise());
        mouvementStocks.setTypeMouvement(mouvementStocksDto.getTypeMouvement());
        mouvementStocks.setTypeMouvement(mouvementStocksDto.getTypeMouvement());
        mouvementStocks.setQuantite(mouvementStocksDto.getQuantite());
        mouvementStocks.setSourceMouvement(mouvementStocks.getSourceMouvement());
        mouvementStocks.setMotif(mouvementStocksDto.getMotif());
        mouvementStocks.setDateMvt(mouvementStocksDto.getDateMvt());
        return mouvementStocks;
    }


}
