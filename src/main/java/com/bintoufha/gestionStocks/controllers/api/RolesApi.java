package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Role", description = "API de gestion des roles")
//@RestController
//@RequestMapping(APP_ROOT + "/roles")
public interface RolesApi {


    @PostMapping(
        value=APP_ROOT + "/roles/create_role",
    consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    ResponseEntity<RoleSaveDto> save(@RequestBody  RoleSaveDto rolesDto);

    @GetMapping(
        value=APP_ROOT + "/roles/recherche/{uuidRole}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<RoleSaveDto> findByUuid(@PathVariable("uuidRole") UUID uuid);

    @GetMapping(
        value=APP_ROOT + "/roles/All_Role",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    ResponseEntity<List<RoleSaveDto>> findAll();

    @DeleteMapping(
        value=APP_ROOT + "/roles/{uuidRole}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<Void> deleteByUuid(@PathVariable("uuidRole") UUID uuid);


    @GetMapping(
            value=APP_ROOT + "/roles/recherche/global",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    public ResponseEntity<List<RoleSaveDto>> getGlobalRoles();


    @PostMapping(
            value=APP_ROOT + "/roles/assigner_permission/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> assignPermissions(
            @PathVariable UUID roleId,
            @RequestBody Set<UUID> permissionIds);

    @GetMapping(value=APP_ROOT + "/roles/role_permission/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE  )
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN_GENERAL')")
    public ResponseEntity<Set<String>> getRolePermissions(@PathVariable UUID roleId);
}
