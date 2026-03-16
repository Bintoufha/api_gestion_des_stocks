package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.Utilisateurs;

public class UtilisateurMapper {

    public static UtilisateurSaveDto fromEntity(Utilisateurs utilisateurs) {
        if (utilisateurs == null) {
            return null;
        }
        return UtilisateurSaveDto.builder()
                .uuid(utilisateurs.getUuid())
                .nomPrenomUtilisateurs(utilisateurs.getNomPrenomUtilisateurs())
                .dateNaissance(utilisateurs.getDateNaissance())
                .email(utilisateurs.getEmail())
                .pwd(utilisateurs.getPwd())
                .telephoneUtilisateurs(utilisateurs.getTelephoneUtilisateurs())
                //.addresse(AddresseDto.fromEntity(utilisateurs.getAddresse()))
                .addresse(
                        AddresseDataDto.builder()
                                .Ville(utilisateurs.getAddresse().getVille())
                                .Pays(utilisateurs.getAddresse().getPays())
                                .CodePostale(utilisateurs.getAddresse().getCodePostale())
                                .Addresse1(utilisateurs.getAddresse().getAddresse1())
                                .Addresse2(utilisateurs.getAddresse().getAddresse2())
                                .build()
                )
                .entreprise(EntrepriseMapper.fromEntity(utilisateurs.getEntreprise()))
//                .roles(
//                        utilisateurs.getRoles() != null ?
//                                utilisateurs.getRoles().stream()
//                                        .map(RolesDto::fromEntity)
//                                        .collect(Collectors.toList()) : null
//                )
                .build();
    }
    public static Utilisateurs toEntity(UtilisateurSaveDto utilisateursDto) {
        if (utilisateursDto == null) {
            return null;
        }
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setUuid(utilisateursDto.getUuid());
        utilisateurs.setEmail(utilisateursDto.getEmail());
        utilisateurs.setPhotoUtilisateurs(utilisateursDto.getPhotoUtilisateurs());
        utilisateurs.setNomPrenomUtilisateurs(utilisateursDto.getNomPrenomUtilisateurs());
        utilisateurs.setDateNaissance(utilisateursDto.getDateNaissance());
        utilisateurs.setPwd(utilisateursDto.getPwd());
        //utilisateurs.setAddresse(AddresseDto.toEntity(utilisateursDto.getAddresse()));
        utilisateurs.setEntreprise(EntrepriseMapper.toEntity(utilisateursDto.getEntreprise()));
        if (utilisateursDto.getAddresse() != null) {
            Addresse addresse = new Addresse();
            addresse.setAddresse1(utilisateursDto.getAddresse().getAddresse1());
            addresse.setVille(utilisateursDto.getAddresse().getVille());
            addresse.setPays(utilisateursDto.getAddresse().getPays());
            addresse.setCodePostale(utilisateursDto.getAddresse().getCodePostale());
        }

//        utilisateurs.setRoles(utilisateursDto.getRoles());
        //clients.setAddresse(clientsDto.toEntity(clientsDto.getAddresse()));
//        utilisateurs.setAddresse(utilisateursDto.getAddresse());
        return utilisateurs;
    }

    public static UtilisateurListDto toListDto(Utilisateurs utilisateurs) {
        if (utilisateurs == null) return null;

        return UtilisateurListDto.builder()
                .uuid(utilisateurs.getUuid())
                .nomPrenomUtilisateurs(utilisateurs.getNomPrenomUtilisateurs())
                .telephoneUtilisateurs(utilisateurs.getTelephoneUtilisateurs())
                .email(utilisateurs.getEmail())
                .dateNaissance(utilisateurs.getDateNaissance())
                .entreprise(
                        EntrepriseSaveDto.builder()
                                .nomEntreprise(utilisateurs.getEntreprise().getNomEntreprise())
                        .build())
                .build();
    }

    public static UtilisateurByEmailDto toEmailtDto(Utilisateurs utilisateurs) {
        if (utilisateurs == null) return null;

        return UtilisateurByEmailDto.builder()
                .uuid(utilisateurs.getUuid())
                .email(utilisateurs.getEmail())
                .pwd(utilisateurs.getPwd())
                .entreprise(
                        EntrepriseSaveDto.builder()
                                .uuid(utilisateurs.getEntreprise().getUuid())
                                .nomEntreprise(utilisateurs.getEntreprise().getNomEntreprise())
                        .build())
                .build();
    }
}
