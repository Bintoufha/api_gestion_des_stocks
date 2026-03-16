package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.utilisateur.ChangerMotDePasseUtilisateurDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Roles;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UtilisateurService {
    UtilisateurSaveDto save (UtilisateurSaveDto utilisateursDto);

    UtilisateurListDto findByUuid(UUID uuid);

    UtilisateurByEmailDto findByEmail(String email);

    UtilisateurSaveDto ChangePwd (ChangerMotDePasseUtilisateurDto pwdChange);

   // User updateUser(Long id, User userDetails)

    List<UtilisateurListDto> findAll();

    void deleteByUuid(UUID uuid);

    void assignRolesToUser(UUID utilisateurUuid ,Set<UUID> roleUuids, UUID entrepriseUuid, UUID assignedByUtilisateurUuid);

    void removeRoleFromUser(UUID utilisateurUuid , UUID roleUuids, UUID entrepriseUuid);

    void deactivateUser(UUID utilisateurUuid);

    void activateUser(UUID utilisateurUuid);

    List<UtilisateurListDto> getUsersByBoutique(UUID entrepriseUuid);

    List<UtilisateurListDto> getUsersByRole(String roleName) ;

    List<Roles> getUserRoles(UUID utilisateurUuid) ;

    Set<String> getUserPermissions(UUID utilisateurUuid);

    boolean userHasRole(UUID utilisateurUuid, String roleName);

    boolean userHasRoleForEntreprise(UUID utilisateurUuid, String roleName, UUID entrepriseUuid);

   void updateLastLogin(UUID utilisateurUuid) ;
}
