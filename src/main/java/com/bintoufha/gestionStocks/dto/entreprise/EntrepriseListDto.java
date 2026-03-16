package com.bintoufha.gestionStocks.dto.entreprise;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.model.StatutEntreprise;
import com.bintoufha.gestionStocks.model.TypeEntreprises;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EntrepriseListDto {

    private UUID uuid;

    private String nomEntreprise;

    private String description;

    private String codeFiscale;

    private String numero;

    private TypeEntreprises typeEntreprises;

    private StatutEntreprise statut;

    private AddresseDataDto addresse;

    private String email;
}
