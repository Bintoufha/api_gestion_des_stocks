package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateursDto utilisateursDto){
        List<String> errors = new ArrayList<>();

        if (utilisateursDto == null){
            errors.add("Veuillez renseigner le champs nom et prenom");
            errors.add("Veuillez renseigner le champs email");
            errors.add("Veuillez renseigner le champs mot de passe");
            errors.add("Veuillez renseigner le champs addresse");
            errors.addAll(AddresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.isEmpty(utilisateursDto.getNomPrenomUtilisateurs())) {
            errors.add("Veuillez renseigner le champs nom et prenom");
        }
        if (!StringUtils.isEmpty(utilisateursDto.getEmail())) {
            errors.add("Veuillez renseigner le champs email");
        }
        if (!StringUtils.isEmpty(utilisateursDto.getPwd())) {
            errors.add("Veuillez renseigner le champs mot de passe");
        }
        if (utilisateursDto.getDateNaissance() == null) {
            errors.add("Veuillez renseigner le champs date de naissance");
        }
        errors.addAll(AddresseValidator.validate(utilisateursDto.getAddresse()));
        return errors;

    }
}
