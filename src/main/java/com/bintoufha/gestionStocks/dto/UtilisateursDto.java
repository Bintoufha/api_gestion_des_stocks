package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.model.Entreprises;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateursDto {
    private UUID uuid;

    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    private String email;

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
                .email(utilisateurs.getEmail())
                .pwd(utilisateurs.getPwd())
                .telephoneUtilisateurs(utilisateurs.getTelephoneUtilisateurs())
                .addresse(AddresseDto.fromEntity(utilisateurs.getAddresse()))
                .entreprise(EntrepriseDto.fromEntity(utilisateurs.getEntreprise()))
//                .roles(
//                        utilisateurs.getRoles() != null ?
//                                utilisateurs.getRoles().stream()
//                                        .map(RolesDto::fromEntity)
//                                        .collect(Collectors.toList()) : null
//                )
                .build();
    }
    public static Utilisateurs fromEntity(UtilisateursDto utilisateursDto) {
        if (utilisateursDto == null) {
            return null;
        }
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setUuid(utilisateursDto.getUuid());
        utilisateurs.setEmail(utilisateursDto.getEmail());
        utilisateurs.setPhotoUtilisateurs(utilisateursDto.getPhotoUtilisateurs());
        utilisateurs.setNomPrenomUtilisateurs(utilisateursDto.getNomPrenomUtilisateurs());
        utilisateurs.setDateNaissance(utilisateursDto.getDateNaissance()
        );
//        utilisateurs.setRoles(utilisateursDto.getRoles());
        //clients.setAddresse(clientsDto.toEntity(clientsDto.getAddresse()));
//        utilisateurs.setAddresse(utilisateursDto.getAddresse());
        return utilisateurs;
    }

}
