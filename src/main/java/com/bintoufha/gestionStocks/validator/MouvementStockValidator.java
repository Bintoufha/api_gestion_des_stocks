package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MouvementStockValidator {
    public static List<String> validate(MouvementStocksDto mvt){
        List<String> errors = new ArrayList<>();
        if (mvt == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
            errors.add("Veuillez renseigner la quantite du mouvenent");
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le type du mouvement");

            return errors;
        }
        if (mvt.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
        }
        if (mvt.getQuantite() == null || mvt.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantite du mouvenent");
        }
        if (mvt.getArticle() == null || mvt.getArticle().getUuid() == null) {
            errors.add("Veuillez renseigner l'article");
        }
        if (!StringUtils.hasLength(mvt.getTypeMouvement().name())) {
            errors.add("Veuillez renseigner le type du mouvement");
        }

        return errors;
    }
}
