package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.Entreprises;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class EntrepriseDto {

    private UUID uuid;

    private String nomEntreprise;

    private String description;

    private String codeFiscale;

    private String photoEntreprise;

    private String numero;

    private String siteWebUrl;

    private AddresseDto addresse;

    private String email;

    public static EntrepriseDto fromEntity(Entreprises entreprises){
        if (entreprises == null){
            return null;
        }

        return EntrepriseDto.builder()
                .uuid(entreprises.getUuid())
                .nomEntreprise(entreprises.getNomEntreprise())
                .numero(entreprises.getNumero())
                .photoEntreprise(entreprises.getPhotoEntreprise())
                .siteWebUrl(entreprises.getSiteWebUrl())
                .addresse(AddresseDto.fromEntity(entreprises.getAddresse()))
                .email(entreprises.getEmail())
                .codeFiscale(entreprises.getCodeFiscale())
                .description(entreprises.getDescription())
                .build();
    }

    public static Entreprises toEntity(EntrepriseDto entrepriseDto){
        if (entrepriseDto == null){
            return null;
        }
        Entreprises entreprises = new Entreprises();
        entreprises.setUuid(entrepriseDto.getUuid());
        entreprises.setNomEntreprise(entrepriseDto.getNomEntreprise());
        entreprises.setAddresse(AddresseDto.toEntity(entrepriseDto.getAddresse()));
        entreprises.setEmail(entrepriseDto.getEmail());
        entreprises.setPhotoEntreprise(entrepriseDto.getPhotoEntreprise());
        entreprises.setCodeFiscale(entrepriseDto.getCodeFiscale());
        entreprises.setDescription(entrepriseDto.getDescription());
        entreprises.setNumero(entrepriseDto.getNumero());
        entreprises.setSiteWebUrl(entrepriseDto.getSiteWebUrl());
        return entreprises;

    }
}
