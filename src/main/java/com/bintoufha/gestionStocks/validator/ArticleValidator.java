package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.CategoriesDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticlesDto Dtoarticles) {
        List<String> errors = new ArrayList<>();
        if (Dtoarticles == null) {
            errors.add("Veuillez renseigner le champs code de article");
            errors.add("Veuillez renseigner la designation de article");
            errors.add("Veuillez renseigner le prix unitaire achat");
            errors.add("Veuillez renseigner le  prix engros ");
            errors.add("Veuillez renseigner le  prix detaillant ");
            errors.add("Veuillez selectionner une categorie");
            return errors;
        }
        if (!StringUtils.isEmpty(Dtoarticles.getNomArticle())) {
            errors.add("Veuillez renseigner le nom de article");
        }
        if (Dtoarticles.getPrixUnitaireArticle() == null) {
            errors.add("Veuillez renseigner le prix unitaire achat article");
        }
        if (Dtoarticles.getPrixEngrosArticle() == null) {
            errors.add("Veuillez renseigner le prix engros de article");
        }
        if (Dtoarticles.getPrixDetailleArticle() == null) {
            errors.add("Veuillez renseigner le prix detaillant de article");
        }
        if (Dtoarticles.getQuantieStocksArticle() == null) {
            errors.add("Veuillez renseigner la quantite de article ");
        }
        if (Dtoarticles.getCategorie() == null) {
            errors.add("Veuillez selectionner une categorie");
        }

        return errors;
    }

}
