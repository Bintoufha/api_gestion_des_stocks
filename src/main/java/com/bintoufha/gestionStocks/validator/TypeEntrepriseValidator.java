package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseSaveDto;

import java.util.ArrayList;
import java.util.List;

public class TypeEntrepriseValidator {

    public static List<String> validate(TypeEntrepriseSaveDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto.getNomTypeEntreprise() == null || dto.getNomTypeEntreprise().isEmpty()) {
            errors.add("Le nom du type de boutique est obligatoire");
        }
        if (dto.getCode() == null || dto.getCode().isEmpty()) {
            errors.add("Le code du type de boutique est obligatoire");
        }
        return errors;
    }
}
