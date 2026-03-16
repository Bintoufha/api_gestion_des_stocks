package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddresseValidator {

    public static List<String> validate(AddresseDataDto adresseDto) {
        List<String> errors = new ArrayList<>();

        if (adresseDto == null) {
            errors.add("Veuillez renseigner l'adresse 1'");
            errors.add("Veuillez renseigner la ville'");
            errors.add("Veuillez renseigner le pays'");
            errors.add("Veuillez renseigner le code postal'");
            return errors;
        }
        if (!StringUtils.hasLength(adresseDto.getAddresse1())) {
            errors.add("Veuillez renseigner l'adresse 1'");
        }
        if (!StringUtils.hasLength(adresseDto.getVille())) {
            errors.add("Veuillez renseigner la ville'");
        }
        if (!StringUtils.hasLength(adresseDto.getPays())) {
            errors.add("Veuillez renseigner le pays'");
        }
        if (!StringUtils.hasLength(adresseDto.getAddresse1())) {
            errors.add("Veuillez renseigner le code postal'");
        }
        return errors;
    }

}
