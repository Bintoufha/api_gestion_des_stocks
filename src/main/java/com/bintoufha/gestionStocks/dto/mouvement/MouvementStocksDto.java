package com.bintoufha.gestionStocks.dto.mouvement;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.SourceMvtStocks;
import com.bintoufha.gestionStocks.model.TypeMvtStocks;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MouvementStocksDto {

    private UUID uuid;

    private Articles article;

    private Instant dateMvt;

    private TypeMvtStocks typeMouvement;

    private SourceMvtStocks sourceMouvement;

    private BigDecimal quantite;

    private String motif;

    private UUID idEntreprise;
}