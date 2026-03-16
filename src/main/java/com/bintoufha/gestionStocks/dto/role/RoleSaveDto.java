package com.bintoufha.gestionStocks.dto.role;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Permission;
import com.bintoufha.gestionStocks.model.RoleScope;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class RoleSaveDto {
    private String nomRole;

    private UtilisateurSaveDto utilisateur;

    private String description;

    private Roles parentRole;

    private RoleScope scope = RoleScope.GLOBAL;

    private Set<Permission> permissions = new HashSet<>();
    

}
