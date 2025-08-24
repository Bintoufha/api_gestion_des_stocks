package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;

import java.util.ArrayList;
import java.util.List;

public class CommandClientsValidator {

    public static List<String> validate(CommandeClientsDto commandeClientsDto) {
        List<String> errors = new ArrayList<>();
        if (commandeClientsDto == null) {
            errors.add("Veuillez renseigner le champs code de article");
            errors.add("Veuillez renseigner la designation de article");
            errors.add("Veuillez renseigner le prix unitaire achat");
            errors.add("Veuillez renseigner le  prix engros ");
            errors.add("Veuillez renseigner le  prix detaillant ");
            errors.add("Veuillez selectionner une categorie");
            return errors;
        }
        return errors;
    }
}
