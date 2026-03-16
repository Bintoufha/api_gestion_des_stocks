package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.UtilisateursApi;
import com.bintoufha.gestionStocks.dto.utilisateur.ChangerMotDePasseUtilisateurDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class UtilisateurControllers implements UtilisateursApi {

    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurControllers(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public ResponseEntity<UtilisateurByEmailDto> findByEmail(String email) {
        return ResponseEntity.ok(utilisateurService.findByEmail(email));
    }

    @Override
    public ResponseEntity<UtilisateurSaveDto> save(UtilisateurSaveDto utilisateursDto) {
        return ResponseEntity.ok(utilisateurService.save(utilisateursDto));
    }

    @Override
    public ResponseEntity<UtilisateurSaveDto> ChangePwd(ChangerMotDePasseUtilisateurDto pwdChange) {
        return ResponseEntity.ok(utilisateurService.ChangePwd(pwdChange));
    }

    @Override
    public ResponseEntity<UtilisateurListDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(utilisateurService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<UtilisateurListDto>> findAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    @Override
    public ResponseEntity<Void> deleteByUuid(UUID uuid) {
        utilisateurService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<UtilisateurListDto>> getUsersByBoutique(UUID entrepriseUuid) {
        return ResponseEntity.ok(utilisateurService.getUsersByBoutique(entrepriseUuid));
    }

    @Override
    public ResponseEntity<Void> assignRolesToUser(UUID utilisateurUuid, Set<UUID> roleUuids, UUID entrepriseUuid, UUID assignedByUtilisateurUuid) {
        utilisateurService.assignRolesToUser(utilisateurUuid, roleUuids, entrepriseUuid, assignedByUtilisateurUuid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Set<String>> getUserPermissions(UUID utilisateurUuid) {
        Set<String> permissions = utilisateurService.getUserPermissions(utilisateurUuid);
        return ResponseEntity.ok(permissions);
    }
}
