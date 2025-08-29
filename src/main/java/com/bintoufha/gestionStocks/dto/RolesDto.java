package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Roles;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RolesDto {

    //private UUID uuid;

    private String nomRole;

    private UUID idEntreprise;

    private UtilisateursDto utilisateur;

    public static RolesDto fromEntity(Roles roles){
        if (roles == null){
            return null;
        }
        return RolesDto.builder()
               // .uuid(roles.getUuid())
                .nomRole(roles.getNomRole())
                .build();
    }

    public static Roles toEntity(RolesDto rolesDto){
        if (rolesDto == null){
            return null;
        }
        Roles roles = new Roles();
        roles.setNomRole(rolesDto.getNomRole());
        return roles;
    }
}
