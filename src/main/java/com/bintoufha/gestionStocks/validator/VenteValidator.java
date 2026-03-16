package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VentesDto vente){
        List<String> errors = new ArrayList<>();
        if (vente == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            return errors;
        }

        if (!StringUtils.hasLength(vente.getReference())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (vente.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        return errors;
    }
}
