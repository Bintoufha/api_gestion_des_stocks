package com.bintoufha.gestionStocks.controllers;


import com.bintoufha.gestionStocks.controllers.api.PermissionApi;
import com.bintoufha.gestionStocks.dto.permission.PermissionListDto;
import com.bintoufha.gestionStocks.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionControllers implements PermissionApi {
    @Autowired
    private PermissionService permissionService;

    @Override
    public ResponseEntity<List<PermissionListDto>> getAllPermissions() {
        List<PermissionListDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @Override
    public ResponseEntity<List<PermissionListDto>> getPermissionsByModule(String module) {
        List<PermissionListDto> permissions = permissionService.getPermissionsByModule(module);
        return ResponseEntity.ok(permissions);
    }

    @Override
    public ResponseEntity<List<PermissionListDto>> getPermissionsByCategory(String category) {
        List<PermissionListDto> permissions = permissionService.getPermissionsByCategory(category);
        return ResponseEntity.ok(permissions);
    }

    @Override
    public ResponseEntity<Void> initializePermissions() {
        permissionService.initializeDefaultPermissions();
        return ResponseEntity.ok().build();
    }
}
