package com.bintoufha.gestionStocks.dto.article;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ArticleCommandeDto {
    private UUID articleId;
    private String nomArticle;
    private BigDecimal prixUnitaire;
    private BigDecimal quantite;
    private BigDecimal montant;
}
