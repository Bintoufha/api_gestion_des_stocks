package com.bintoufha.gestionStocks.dto.entreprise;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.model.TypeEntreprises;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class EntrepriseSaveDto {

    private UUID uuid;

    private String nomEntreprise;

    private String description;

    private String codeFiscale;

    private String photoEntreprise;

    private TypeEntreprises typeEntreprises;


    private String numero;

    private String siteWebUrl;

    private AddresseDataDto addresse;

    private String email;
}
