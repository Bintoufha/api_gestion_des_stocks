package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.RolesApi;
import com.bintoufha.gestionStocks.dto.RolesDto;
import com.bintoufha.gestionStocks.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RoleControllers implements RolesApi {

    private RoleService roleService;

    @Autowired
    public RoleControllers(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<RolesDto> save(RolesDto rolesDto) {
        return ResponseEntity.ok(roleService.save(rolesDto));
    }

    @Override
    public ResponseEntity<RolesDto> findByUuid(UUID uuid) {
        return ResponseEntity.ok(roleService.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<List<RolesDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Override
    public ResponseEntity<RolesDto> deleteByUuid(UUID uuid) {
        roleService.deleteByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
