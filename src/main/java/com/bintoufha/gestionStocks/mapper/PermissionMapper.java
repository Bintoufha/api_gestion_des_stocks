package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.permission.PermissionListDto;
import com.bintoufha.gestionStocks.dto.permission.PermissionSaveDto;
import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Permission;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.model.Utilisateurs;

public class PermissionMapper {

    public static PermissionSaveDto fromEntity(Permission permission){
        if (permission == null){
            return null;
        }
        return PermissionSaveDto.builder()
                .name(permission.getName())
                .code(permission.getCode())
                .action(permission.getAction())
                .category(permission.getCategory())
                .module(permission.getModule())
                .description(permission.getDescription())
                .build();
    }

    public static Permission toEntity(PermissionSaveDto permissionSaveDto){
        if (permissionSaveDto == null){
            return null;
        }
        Permission permission = new Permission();
        permission.setName(permissionSaveDto.getName());
        permission.setCode(permissionSaveDto.getCode());
        permission.setDescription(permissionSaveDto.getDescription());
        permission.setModule(permissionSaveDto.getModule());
        permission.setCategory(permissionSaveDto.getCategory());
        permission.setAction(permissionSaveDto.getAction());

        return permission;
    }

    public static PermissionListDto toListDto(Permission permission) {
        if (permission == null) return null;

        return PermissionListDto.builder()
                .name(permission.getName())
                .code(permission.getCode())
                .action(permission.getAction())
                .description(permission.getDescription())
                .build();
    }
}
