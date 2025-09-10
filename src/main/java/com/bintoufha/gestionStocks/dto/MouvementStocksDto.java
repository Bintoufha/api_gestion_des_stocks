package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.MouvementStocks;
import com.bintoufha.gestionStocks.model.SourceMvtStocks;
import com.bintoufha.gestionStocks.model.TypeMvtStocks;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder

public class MouvementStocksDto {

    private UUID uuid;

    private Articles article;

    private Instant dateMvt;

    private TypeMvtStocks typeMouvement;

    private SourceMvtStocks sourceMouvement;

    private BigDecimal quantite;

    private String motif;

    private UUID idEntreprise;

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
