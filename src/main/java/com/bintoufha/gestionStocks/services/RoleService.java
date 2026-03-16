package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.model.Permission;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {

    RoleSaveDto save (RoleSaveDto rolesDto);

    RoleSaveDto findByUuid(UUID uuid);

    List<RoleSaveDto> findAll();

    List<RoleSaveDto> getGlobalRoles();

    List<RoleSaveDto> getChildRoles(UUID uuid);


    Set<Permission> getRolePermissions(UUID roleUuid);

    void deleteByUuid(UUID uuid);

    void assignPermissionsToRole(UUID roleUuid, Set<UUID> permissionUuids);

    void removePermissionFromRole(UUID roleUuid, UUID permissionUuid);

    boolean roleHasPermission(UUID roleUuid, String permissionCode);
}
