package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import com.bintoufha.gestionStocks.model.MouvementStocks;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder

public class MouvementStocksDto {

    private UUID uuid;

    private Articles article;

    private String typeMouvement;

    private  Integer quantite;

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
        mouvementStocks.setQuantite(mouvementStocksDto.getQuantite());
        mouvementStocks.setMotif(mouvementStocksDto.getMotif());
        return mouvementStocks;
    }
}
