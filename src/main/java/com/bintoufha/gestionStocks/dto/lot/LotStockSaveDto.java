package com.bintoufha.gestionStocks.dto.lot;

import java.math.BigDecimal;
import java.util.UUID;

import com.bintoufha.gestionStocks.model.Articles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LotStockSaveDto {
    private UUID uuid;

    private Articles articles;

    private UUID idEntreprise;

    private BigDecimal qteCommande;

    private BigDecimal qteRestant;

    private BigDecimal prixUnitaire;
}