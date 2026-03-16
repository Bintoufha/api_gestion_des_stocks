package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.mapper.RoleMapper;
import com.bintoufha.gestionStocks.mapper.UtilisateurMapper;
import com.bintoufha.gestionStocks.model.Permission;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.repository.PermissionRepository;
import com.bintoufha.gestionStocks.repository.RoleRepository;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final DefaultErrorAttributes errorAttributes;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private UtilisateursRepository utilisateursRepository;

    public RoleServiceImpl(
            DefaultErrorAttributes errorAttributes,
            RoleRepository roleRepository,
            UtilisateursRepository utilisateursRepository,
            PermissionRepository permissionRepository
    ) {
        this.errorAttributes = errorAttributes;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.utilisateursRepository = utilisateursRepository;
    }

    @Override
    public RoleSaveDto save(RoleSaveDto rolesDto) {
        // Vérifier unicité
        if (roleRepository.existsByName(rolesDto.getNomRole())) {
            throw new RuntimeException("Un rôle avec ce nom existe déjà");
        }

        // Convertir DTO -> Entité
        Roles role = RoleMapper.toEntity(rolesDto);

        // Gérer le parent séparément si nécessaire
        if (rolesDto.getParentRole() != null && rolesDto.getParentRole().getUuid() != null) {
            Roles parent = roleRepository.findByUuid(rolesDto.getParentRole().getUuid())
                    .orElseThrow(() -> new RuntimeException("Rôle parent non trouvé"));
            role.setParentRole(parent);
        }

        // Sauvegarder
        Roles savedRole = roleRepository.save(role);

        // Convertir Entité -> DTO
        return RoleMapper.fromEntity(savedRole);

    }

    @Override
    public RoleSaveDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("role ID is null");
            return null;
        }
        return roleRepository.findByUuid(uuid)
                .map(RoleMapper::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun role avec l'ID = " + uuid + " n' ete trouve dans la BDD",
                        ErrorCodes.ROLE_NOT_FOUND)
                );
    }

    @Override
    public List<RoleSaveDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(role -> {
                    // ou via TarificationService si besoin
                    return RoleMapper.fromEntity(role);
                })
                .toList();
    }

    @Override
    public List<RoleSaveDto> getGlobalRoles() {
        return roleRepository.findGlobalRoles()
                .stream()
                .map(roleGlobal -> {
                    // ou via TarificationService si besoin
                    return RoleMapper.fromEntity(roleGlobal);
                })
                .toList();
    }

    @Override
    public List<RoleSaveDto> getChildRoles(UUID uuid) {
        return roleRepository.findByParentUuid(uuid)
                .stream()
                .map(roleGlobal -> {
                    // ou via TarificationService si besoin
                    return RoleMapper.fromEntity(roleGlobal);
                })
                .toList();
    }

    @Override
    public Set<Permission> getRolePermissions(UUID roleUuid) {
        Roles role = roleRepository.findByUuid(roleUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun role avec l'ID = " + roleUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.ROLE_NOT_FOUND));

        return role.getAllPermissions();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        Roles role = roleRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));

        // Vérifier si utilisé
        if (!role.getChildRoles().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : ce rôle a des rôles enfants");
        }
        // Vérifier si des utilisateurs sont assignés à ce rôle
        boolean hasUsers = utilisateursRepository.existsByRoles(role);
        if (hasUsers) {
            throw new RuntimeException("Impossible de supprimer : des utilisateurs sont assignés à ce rôle");
        }
        roleRepository.delete(role);
    }

    @Override
    public void assignPermissionsToRole(UUID roleUuid, Set<UUID> permissionUuids) {
        Roles role = roleRepository.findByUuid(roleUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun role avec l'ID = " + roleUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.ROLE_NOT_FOUND));

        Set<Permission> permissions = permissionUuids.stream()
                .map(permissionId -> permissionRepository.findByUuid(permissionId)
                        .orElseThrow(() -> new EntityNoFoundException(
                                "Aucun permission avec l'ID = " + roleUuid + " n' ete trouve dans la BDD",
                                ErrorCodes.ROLE_NOT_FOUND)))
                .collect(java.util.stream.Collectors.toSet());

        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    @Override
    public void removePermissionFromRole(UUID roleUuid, UUID permissionUuid) {
        Roles role = roleRepository.findByUuid(permissionUuid)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));

        Permission permission = permissionRepository.findByUuid(permissionUuid)
                .orElseThrow(() -> new RuntimeException("Permission non trouvée"));

        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }

    @Override
    public boolean roleHasPermission(UUID roleUuid, String permissionCode) {
        Roles role = roleRepository.findByUuid(roleUuid)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));

        return role.hasPermission(permissionCode);
    }
}
