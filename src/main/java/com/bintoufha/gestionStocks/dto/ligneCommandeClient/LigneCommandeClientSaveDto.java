package com.bintoufha.gestionStocks.dto.ligneCommandeClient;

import java.math.BigDecimal;
import java.util.UUID;

import com.bintoufha.gestionStocks.model.Articles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeClientSaveDto {
    private UUID uuid;
    private Articles article;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
}