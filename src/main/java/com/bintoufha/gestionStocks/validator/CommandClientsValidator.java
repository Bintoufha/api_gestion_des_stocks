package com.bintoufha.gestionStocks.validator;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.hasLength(commandeClientsDto.getRefernce())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (commandeClientsDto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (!StringUtils.hasLength(commandeClientsDto.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if (commandeClientsDto.getClients() == null || commandeClientsDto.getClients().getUuid() == null) {
            errors.add("Veuillez renseigner le client");
        }
        return errors;
    }
}
