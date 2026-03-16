package com.bintoufha.gestionStocks.dto.article;

import com.bintoufha.gestionStocks.dto.categorie.CategorieInfoDto;
import com.bintoufha.gestionStocks.model.Categories;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ArticleSaveDto {

    private UUID uuid;

    private String nomArticle;

    private BigDecimal prixUnitaireArticle;

    private BigDecimal seuilRuptureArticle;

    private BigDecimal quantieStocksArticle;

    private Integer piece;

    private UUID idEntreprise;

    private UUID categorieUuid;
}
