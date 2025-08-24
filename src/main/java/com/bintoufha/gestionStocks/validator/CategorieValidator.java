package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategorieValidator {
    public static List<String> validate(CategoriesDto categoriesDto){
        List<String> errors = new ArrayList<>();
        if (categoriesDto == null || StringUtils.isEmpty(categoriesDto.getCode())){
            errors.add("Veuillez renseigner le code de la categorie");
        }
        return errors;
    }
}
