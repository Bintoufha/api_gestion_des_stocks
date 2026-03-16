package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;
import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import com.bintoufha.gestionStocks.model.LigneVente;

public class LigneVenteMapper {
    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        if (ligneVente == null) {
            return null;
        }
        return LigneVenteDto.builder()
                .qte(ligneVente.getQte())
                .prix(ligneVente.getPrix())
                .articles(
                        ArticleSaveDto.builder()
                                .uuid(ligneVente.getUuid())
                                .build()
                )
                .vente(
                        VentesDto.builder()
                                .uuid(ligneVente.getUuid())
                                .build()
                )
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {
        if (ligneVenteDto == null) {
            return null;
        }
        LigneVente ligneVente = new LigneVente();
        ligneVente.setUuid(ligneVenteDto.getUuid());
        // ligneVente.setArticles(ArticlesDto.toEntity(ligneVenteDto.getArticles()));
        //ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
        ligneVente.setPrix(ligneVenteDto.getPrix());
        ligneVente.setQte(ligneVenteDto.getQte());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());
//        ligneVente.setValorisationMethod(ligneVenteDto.getValorisationMethod());
        return ligneVente;
    }
}
