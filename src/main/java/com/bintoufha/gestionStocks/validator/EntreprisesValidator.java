package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntreprisesValidator {

    public static List<String> validate(EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();
        if (entrepriseDto == null){
            errors.add("Veuillez renseigner le nom de entreprise");;
            return errors;
        }
//        if (!StringUtils.isEmpty(entrepriseDto.getNomEntreprise())) {
//            errors.add("Veuillez renseigner le nom de entreprise");
//        }
//        if (!StringUtils.isEmpty(entrepriseDto.getAddresse())) {
//            errors.add("Veuillez renseigner le mail de client");
//        }
//        if (!StringUtils.isEmpty(entrepriseDto.getTelephoneClient())) {
//            errors.add("Veuillez renseigner le mail de client");
//        }
//        if (!StringUtils.isEmpty(entrepriseDto.getTelephoneClient())) {
//            errors.add("Veuillez renseigner le mail de client");
//        }
//        if (!StringUtils.isEmpty(entrepriseDto.getTelephoneClient())) {
//            errors.add("Veuillez renseigner le mail de client");
//        }
//        if (!StringUtils.isEmpty(entrepriseDto.getTelephoneClient())) {
//            errors.add("Veuillez renseigner le mail de client");
//        }
        return errors;
    }
}
