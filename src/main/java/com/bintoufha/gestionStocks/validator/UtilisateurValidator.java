package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurSaveDto utilisateursDto){
        List<String> errors = new ArrayList<>();

        if (utilisateursDto == null){
            errors.add("Veuillez renseigner le champs nom et prenom");
            errors.add("Veuillez renseigner le champs email");
            errors.add("Veuillez renseigner le champs mot de passe");
            errors.add("Veuillez renseigner le champs addresse");
            errors.addAll(AddresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.hasLength(utilisateursDto.getNomPrenomUtilisateurs())) {
            errors.add("Veuillez renseigner le champs nom et prenom");
        }
        if (!StringUtils.hasLength(utilisateursDto.getEmail())) {
            errors.add("Veuillez renseigner le champs email");
        }
        if (!StringUtils.hasLength(utilisateursDto.getPwd())) {
            errors.add("Veuillez renseigner le champs mot de passe");
        }
        if (utilisateursDto.getDateNaissance() == null) {
            errors.add("Veuillez renseigner le champs date de naissance");
        }
        if (utilisateursDto.getEntreprise() == null || utilisateursDto.getEntreprise().getUuid()== null) {
            errors.add("Veuillez renseigner le champs de l'entreprise");
        }
        errors.addAll(AddresseValidator.validate(utilisateursDto.getAddresse()));
        return errors;

    }
}
