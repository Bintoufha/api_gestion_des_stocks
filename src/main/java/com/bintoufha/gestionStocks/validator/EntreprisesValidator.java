package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntreprisesValidator {

    public static List<String> validate(EntrepriseSaveDto entrepriseDto){
        List<String> errors = new ArrayList<>();
        if (entrepriseDto == null){
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez reseigner la description de l'entreprise");
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
            errors.add("Veuillez reseigner l'email de l'entreprise");
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
            errors.addAll(AddresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.hasLength(entrepriseDto.getNomEntreprise())) {
            errors.add("Veuillez renseigner le nom de entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDto.getDescription())) {
            errors.add("Veuillez reseigner la description de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDto.getCodeFiscale())) {
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDto.getEmail())) {
            errors.add("Veuillez reseigner l'email de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDto.getNumero())) {
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
        }

        errors.addAll(AddresseValidator.validate(entrepriseDto.getAddresse()));
        return errors;
    }
}
