package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.RolesApi;
import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class RoleControllers implements RolesApi {

    private RoleService roleService;

    @Autowired
    public RoleControllers(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<RoleSaveDto> save(RoleSaveDto rolesDto) {
        return ResponseEntity.ok(roleService.save(rolesDto));
    }

    @Override
    public ResponseEntity<RoleSaveDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(roleService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<RoleSaveDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Override
    public ResponseEntity<Void> deleteByUuid(UUID uuid) {
        roleService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RoleSaveDto>> getGlobalRoles() {
        List<RoleSaveDto> roles = roleService.getGlobalRoles();
        return ResponseEntity.ok(roles);
    }


    @Override
    public ResponseEntity<Void> assignPermissions(UUID roleId, Set<UUID> permissionIds) {
        roleService.assignPermissionsToRole(roleId,permissionIds);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Set<String>> getRolePermissions(UUID roleId) {
        Set<String> permissions = roleService.getRolePermissions(roleId).stream()
                .map(permission -> permission.getCode())
                .collect(java.util.stream.Collectors.toSet());
        return ResponseEntity.ok(permissions);
    }


}
