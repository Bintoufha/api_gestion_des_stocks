package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.dto.FournisseursDto;
import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseursDto Dtofournissuers){
        List<String> errors = new ArrayList<>();
        if (Dtofournissuers == null){
            errors.add("Veuillez renseigner le nom et prenom du fournisseur");
            errors.add("Veuillez renseigner le mail de fournisseur ");
            errors.add("Veuillez renseigner le numero de telephone du fournisseur ");
            return errors;
        }
        if (!StringUtils.isEmpty(Dtofournissuers.getNomPrenomFournisseurs())) {
            errors.add("Veuillez renseigner le nom et prenom du fournisseur");
        }
        if (!StringUtils.isEmpty(Dtofournissuers.getEmailFournisseurs())) {
            errors.add("Veuillez renseigner le mail de fournisseur");
        }
        if (!StringUtils.isEmpty(Dtofournissuers.getTelephoneFournisseurs())) {
            errors.add("Veuillez renseigner le mail de fournisseur");
        }
        return errors;
    }
}
