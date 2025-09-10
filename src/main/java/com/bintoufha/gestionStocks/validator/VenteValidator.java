package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.VentesDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VentesDto vente){
        List<String> errors = new ArrayList<>();
        if (vente == null){
            errors.add("Veuillez renseigner tout le champs");
        }
        return errors;
    }
}
