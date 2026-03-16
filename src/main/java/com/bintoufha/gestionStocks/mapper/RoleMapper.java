package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.model.Utilisateurs;

public class RoleMapper {

    public static RoleSaveDto fromEntity(Roles roles){
        if (roles == null){
            return null;
        }
        return RoleSaveDto.builder()
                // .uuid(roles.getUuid())
                .nomRole(roles.getNomRole())
                .utilisateur(
                        UtilisateurSaveDto.builder()
                                .uuid(roles.getUuid())
                                .build()
                )
                .scope(roles.getScope())
                .parentRole(roles.getParentRole())
                .description(roles.getDescription())
                .build();
    }

    public static Roles toEntity(RoleSaveDto rolesDto){
        if (rolesDto == null){
            return null;
        }
        Roles roles = new Roles();
        roles.setNomRole(rolesDto.getNomRole());
        roles.setDescription(rolesDto.getDescription());
        roles.setUtilisateurs(
                Utilisateurs.builder()
                        .nomPrenomUtilisateurs(rolesDto.getUtilisateur().getNomPrenomUtilisateurs())
                        .email(rolesDto.getUtilisateur().getEmail())
                        .build()
        );
        return roles;
    }

    public static RoleSaveDto toListDto(Roles role) {
        if (role == null) return null;

        return RoleSaveDto.builder()
                .nomRole(role.getNomRole())
                .utilisateur(UtilisateurSaveDto.builder()
                        .nomPrenomUtilisateurs(role.getUtilisateurs().getNomPrenomUtilisateurs())
                        .email(role.getUtilisateurs().getEmail())
                        .build())
                .build();
    }
}
