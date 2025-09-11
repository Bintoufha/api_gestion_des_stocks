package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneVente;
import com.bintoufha.gestionStocks.model.Ventes;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LigneVenteDto {
    private UUID uuid;

    private VentesDto vente;


    private ArticlesDto articles;


    private BigDecimal qte;

    private UUID idEntreprise;


    private BigDecimal prix;

    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        if (ligneVente == null) {
            return null;
        }
        return LigneVenteDto.builder()
                .uuid(ligneVente.getUuid())
                .articles(ArticlesDto.fromEntity(ligneVente.getArticles()))
                .vente(VentesDto.fromEntity(ligneVente.getVente()))
                .idEntreprise(ligneVente.getIdEntreprise())
                .qte(ligneVente.getQte())
                .prix(ligneVente.getPrix())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {
        if (ligneVenteDto == null) {
            return null;
        }
        LigneVente ligneVente = new LigneVente();
        ligneVente.setUuid(ligneVenteDto.getUuid());
        ligneVente.setArticles(ArticlesDto.toEntity(ligneVenteDto.getArticles()));
        //ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
        ligneVente.setPrix(ligneVenteDto.getPrix());
        ligneVente.setQte(ligneVenteDto.getQte());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());
        return ligneVente;
    }
}
