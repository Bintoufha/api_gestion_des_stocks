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

    private Ventes vente;


    private Articles articles;


    private BigDecimal Qte;

    private UUID idEntreprise;


    private BigDecimal prix;

    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        if (ligneVente == null) {
            return null;
        }
        return LigneVenteDto.builder()
                .uuid(ligneVente.getUuid())
                .articles(ligneVente.getArticles())
                .idEntreprise(ligneVente.getIdEntreprise())
                .Qte(ligneVente.getQte())
                .prix(ligneVente.getPrix())
                .vente(ligneVente.getVente())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {
        if (ligneVenteDto == null) {
            return null;
        }
        LigneVente ligneVente = new LigneVente();
        ligneVente.setUuid(ligneVenteDto.getUuid());
        ligneVente.setVente(ligneVenteDto.getVente());
        ligneVente.setPrix(ligneVenteDto.getPrix());
        ligneVente.setQte(ligneVenteDto.getQte());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());
        return ligneVente;
    }
}
