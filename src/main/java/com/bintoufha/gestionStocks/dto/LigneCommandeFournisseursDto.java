package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;

import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LigneCommandeFournisseursDto {
    private UUID uuid;

    private Articles article;

    private UUID idEntreprise;

    private CommandeFournisseursDto commandefournisseur;

    public static LigneCommandeFournisseursDto fromEntity (LigneCommandeFournisseurs ligneCommandeFournisseurs){
        if (ligneCommandeFournisseurs == null){
            return null;
        }
        return LigneCommandeFournisseursDto.builder()
                .uuid(ligneCommandeFournisseurs.getUuid())
                .article(ligneCommandeFournisseurs.getArticles())
                .idEntreprise(ligneCommandeFournisseurs.getIdEntreprise())
                .build();
    }
    public static LigneCommandeFournisseurs toEntity (LigneCommandeFournisseursDto ligneCommandeFournisseursDto){
        if (ligneCommandeFournisseursDto == null){
            return null;
        }
        LigneCommandeFournisseurs ligneCommandeFournisseurs = new LigneCommandeFournisseurs();
        ligneCommandeFournisseurs.setUuid(ligneCommandeFournisseursDto.getUuid());
        ligneCommandeFournisseurs.setArticles(ligneCommandeFournisseursDto.getArticle());
        ligneCommandeFournisseurs.setIdEntreprise(ligneCommandeFournisseursDto.getIdEntreprise());
        return ligneCommandeFournisseurs;
    }
}
