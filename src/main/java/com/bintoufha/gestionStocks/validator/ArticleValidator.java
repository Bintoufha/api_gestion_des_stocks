package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleSaveDto dtoArticle) {
        List<String> errors = new ArrayList<>();

        if (dtoArticle == null) {
            errors.add("Veuillez renseigner le nom de l’article.");
            errors.add("Veuillez renseigner le prix unitaire de l’article.");
            errors.add("Veuillez renseigner le prix en gros de l’article.");
            errors.add("Veuillez renseigner le prix détaillant de l’article.");
            errors.add("Veuillez renseigner la quantité en stock.");
            errors.add("Veuillez sélectionner une catégorie.");
            return errors;
        }

        if (!StringUtils.hasLength(dtoArticle.getNomArticle())) {
            errors.add("Veuillez renseigner le nom de l’article.");
        }

        // ✅ Comparaisons BigDecimal avec compareTo()
        
        if (dtoArticle.getPrixUnitaireArticle() == null ||
            dtoArticle.getPrixUnitaireArticle().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Veuillez renseigner un prix unitaire valide (> 0).");
        }


        if (dtoArticle.getQuantieStocksArticle() == null ||
            dtoArticle.getQuantieStocksArticle().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Veuillez renseigner une quantité en stock valide (≥ 0).");
        }

        if (dtoArticle.getCategorieUuid()== null) {
            errors.add("Veuillez sélectionner une catégorie.");
        }

        return errors;
    }
}
