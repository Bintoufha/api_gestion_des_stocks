package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.permission.PermissionListDto;
import com.bintoufha.gestionStocks.dto.permission.PermissionSaveDto;
import com.bintoufha.gestionStocks.model.Permission;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionSaveDto save (PermissionSaveDto permissionDto);

    PermissionListDto getPermission(UUID uuid);

    List<PermissionListDto> getAllPermissions();

    List<PermissionListDto> getPermissionsByModule(String module);

    List<PermissionListDto> getPermissionsByCategory(String category);

    List<PermissionListDto> getPermissionsByModuleAndCategory(String module, String category);

    List<PermissionListDto> getPermissionsByRole(String roleName);

    void deletePermission(UUID uuid);

    void initializeDefaultPermissions();
}
