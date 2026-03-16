package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.categorie.CategorieSaveDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategorieValidator {
    public static List<String> validate(CategorieSaveDto categorieSaveDto){
        List<String> errors = new ArrayList<>();
        if (categorieSaveDto == null || StringUtils.isEmpty(categorieSaveDto.getCode())){
            errors.add("Veuillez renseigner le code de la categorie");
        }
        return errors;
    }
}
