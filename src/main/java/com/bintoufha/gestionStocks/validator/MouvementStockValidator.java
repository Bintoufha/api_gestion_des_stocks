package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MouvementStockValidator {
    public static List<String> validate(MouvementStocksDto mvt){
        List<String> errors = new ArrayList<>();
        if (mvt == null || StringUtils.isEmpty(mvt.getTypeMouvement())){
            errors.add("Veuillez renseigner le type de mouvement");
        }
        return errors;
    }
}
