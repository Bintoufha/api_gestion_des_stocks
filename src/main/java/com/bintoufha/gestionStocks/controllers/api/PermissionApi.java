package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.permission.PermissionListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PermissionApi {


    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    ResponseEntity<List<PermissionListDto>> getAllPermissions();

    @GetMapping("/module/{module}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    public ResponseEntity<List<PermissionListDto>> getPermissionsByModule(@PathVariable String module);

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    public ResponseEntity<List<PermissionListDto>> getPermissionsByCategory(@PathVariable String category);

    @GetMapping("/initialize")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> initializePermissions();
}
