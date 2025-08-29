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
        if (utilisateursDto.getAddresse() == null) {
            errors.add("Veuillez renseigner le champs nom et prenom");
        }else {
            if (!StringUtils.isEmpty(utilisateursDto.getAddresse().getAddresse1())) {
                errors.add("le champs addresse 1 est obligatoire");
            }
            if (!StringUtils.isEmpty(utilisateursDto.getAddresse().getCodePostale())) {
                errors.add("le champs Code postale est obligatoire");
            }
            if (!StringUtils.isEmpty(utilisateursDto.getAddresse().getVille())) {
                errors.add("le champs Ville est obligatoire");
            }
            if (!StringUtils.isEmpty(utilisateursDto.getAddresse().getPays())) {
                errors.add("le champs Pays 1 est obligatoire");
            }
            if (!StringUtils.isEmpty(utilisateursDto.getAddresse().getAddresse1())) {
                errors.add("le champs addresse 1 est obligatoire");
            }

        }
        return errors;

    }
}
