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


    private AddresseDto addresse;

    private String email;

    public static EntrepriseDto fromEntity(Entreprises entreprises){
        if (entreprises == null){
            return null;
        }

        return EntrepriseDto.builder()
                .uuid(entreprises.getUuid())
                .nomEntreprise(entreprises.getNomEntreprise())
                //.addresse(entreprises.getAddresse())
                .email(entreprises.getEmail())
//                .dateN
                .build();
    }

    public static Entreprises toEntity(EntrepriseDto entrepriseDto){
        if (entrepriseDto == null){
            return null;
        }
        Entreprises entreprises = new Entreprises();
        entreprises.setUuid(entrepriseDto.getUuid());
        entreprises.setNomEntreprise(entrepriseDto.getNomEntreprise());
        //entreprises.setAddresse(entrepriseDto.getAddresse());
        entreprises.setEmail(entrepriseDto.getEmail());
        return entreprises;

    }
}
