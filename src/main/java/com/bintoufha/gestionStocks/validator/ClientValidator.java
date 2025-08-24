package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.ClientsDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientsDto Dtoclients){
        List<String> errors = new ArrayList<>();
        if (Dtoclients == null){
            errors.add("Veuillez renseigner le nom et prenom du client");
            errors.add("Veuillez renseigner le mail de client ");
            errors.add("Veuillez renseigner le numero de telephone ");
            return errors;
        }
        if (!StringUtils.isEmpty(Dtoclients.getNomPrenomClient())) {
            errors.add("Veuillez renseigner le nom et prenom du client");
        }
        if (!StringUtils.isEmpty(Dtoclients.getEmailClient())) {
            errors.add("Veuillez renseigner le mail de client");
        }
        if (!StringUtils.isEmpty(Dtoclients.getTelephoneClient())) {
            errors.add("Veuillez renseigner le mail de client");
        }
        return errors;
    }
}
