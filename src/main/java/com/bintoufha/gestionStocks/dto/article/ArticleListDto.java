package com.bintoufha.gestionStocks.dto.article;

import com.bintoufha.gestionStocks.dto.categorie.CategorieInfoDto;
import com.bintoufha.gestionStocks.model.Categories;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ArticleListDto {
    private UUID uuid;

    private String nomArticle;

    private BigDecimal prixUnitaireArticle;

//    private Categories categorie;

    private CategorieInfoDto categorie;
}
