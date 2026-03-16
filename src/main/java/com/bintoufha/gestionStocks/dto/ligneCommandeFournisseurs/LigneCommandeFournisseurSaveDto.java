package com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.bintoufha.gestionStocks.model.Articles;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LigneCommandeFournisseurSaveDto {
    private UUID uuid;

    private Articles article;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

}