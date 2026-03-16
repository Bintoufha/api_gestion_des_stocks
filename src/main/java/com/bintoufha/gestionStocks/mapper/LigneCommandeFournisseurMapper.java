package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs.LigneCommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;

public class LigneCommandeFournisseurMapper {

    public static LigneCommandeFournisseurs toEntity(
            LigneCommandeFournisseurSaveDto dto,
            Articles article
    ) {
        if (dto == null) return null;

        LigneCommandeFournisseurs ligne = new LigneCommandeFournisseurs();

        ligne.setUuid(dto.getUuid());  // facultatif si généré par la DB
        ligne.setArticles(article);    // l’article doit être récupéré via articleUuid
        ligne.setQuantite(dto.getQuantite());
        ligne.setPrixUnitaire(dto.getPrixUnitaire());

        return ligne;
    }

    public static LigneCommandeFournisseurSaveDto fromEntity (LigneCommandeFournisseurs ligneCommandeFournisseurs){
        if (ligneCommandeFournisseurs == null){
            return null;
        }
        return LigneCommandeFournisseurSaveDto.builder()
                .uuid(ligneCommandeFournisseurs.getUuid())
                .article(ligneCommandeFournisseurs.getArticles())
                .quantite(ligneCommandeFournisseurs.getQuantite())
                .prixUnitaire(ligneCommandeFournisseurs.getPrixUnitaire())
                .build();
    }
}
