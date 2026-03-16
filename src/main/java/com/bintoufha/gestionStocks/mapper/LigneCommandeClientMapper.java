package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;

public class LigneCommandeClientMapper {
    private LigneCommandeClientMapper() {
    }

    public static LigneCommandeClients toEntity(LigneCommandeClientSaveDto dto, Articles article) {
        if (dto == null) {
            return null;
        }
        LigneCommandeClients ligne = new LigneCommandeClients();
        ligne.setUuid(dto.getUuid());
        ligne.setArticles(article);
        ligne.setQuantite(dto.getQuantite());
        ligne.setPrixUnitaire(dto.getPrixUnitaire());

        return ligne;
    }

    public static LigneCommandeClientSaveDto fromEntity (LigneCommandeClients ligneCommandeClients){
        if (ligneCommandeClients == null){
            return null;
        }
        return LigneCommandeClientSaveDto.builder()
                .uuid(ligneCommandeClients.getUuid())
                .article(ligneCommandeClients.getArticles())
                .quantite(ligneCommandeClients.getQuantite())
                .prixUnitaire(ligneCommandeClients.getPrixUnitaire())
                .build();
    }
}
