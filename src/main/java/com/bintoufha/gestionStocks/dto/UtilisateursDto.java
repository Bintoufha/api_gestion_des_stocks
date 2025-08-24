package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.model.Entreprises;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateursDto {
    private UUID uuid;

    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    private String emailUtilisateurs;

    private String pwd;

    private String telephoneUtilisateurs;

    private AddresseDto addresse;

    private EntrepriseDto entreprise;

    private String photoUtilisateurs;

    private List<RolesDto> roles;

    public static UtilisateursDto fromEntity(Utilisateurs utilisateurs) {
        if (utilisateurs == null) {
            return null;
        }
        return UtilisateursDto.builder()
                .uuid(utilisateurs.getUuid())
                .nomPrenomUtilisateurs(utilisateurs.getNomPrenomUtilisateurs())
                .dateNaissance(utilisateurs.getDateNaissance())
                .emailUtilisateurs(utilisateurs.getEmailUtilisateurs())
                .pwd(utilisateurs.getPwd())
                .telephoneUtilisateurs(utilisateurs.getTelephoneUtilisateurs())
                .addresse(AddresseDto.fromEntity(utilisateurs.getAddresse()))
                .entreprise(EntrepriseDto.fromEntity(utilisateurs.getEntreprise()))
                .roles(
                        utilisateurs.getRoles() != null ?
                                utilisateurs.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }
}
