package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Articles;

import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LigneCommandeClientsDto {

    private UUID uuid;

    private Articles article;

    private BigDecimal quantite;

    private  BigDecimal prixUnitaire;

    private UUID idEntreprise;

    @JsonIgnore
    private CommandeClientsDto commandeClients;

    public static LigneCommandeClientsDto fromEntity (LigneCommandeClients ligneCommandeClients){
        if (ligneCommandeClients == null){
            return null;
        }
        return LigneCommandeClientsDto.builder()
                .uuid(ligneCommandeClients.getUuid())
                .article(ligneCommandeClients.getArticles())
                .quantite(ligneCommandeClients.getQuantite())
                .prixUnitaire(ligneCommandeClients.getPrixUnitaire())
                .build();
    }
    public static LigneCommandeClients toEntity (LigneCommandeClientsDto ligneCommandeClientsDto){
        if (ligneCommandeClientsDto == null){
            return null;
        }
        LigneCommandeClients ligneCommandeClients = new LigneCommandeClients();
        ligneCommandeClients.setUuid(ligneCommandeClientsDto.getUuid());
        ligneCommandeClients.setArticles(ligneCommandeClientsDto.getArticle());
        ligneCommandeClients.setQuantite(ligneCommandeClientsDto.getQuantite());
        ligneCommandeClients.setPrixUnitaire(ligneCommandeClientsDto.getPrixUnitaire());
        return ligneCommandeClients;
    }
}
