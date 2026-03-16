package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.permission.PermissionListDto;
import com.bintoufha.gestionStocks.dto.permission.PermissionSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.mapper.PermissionMapper;
import com.bintoufha.gestionStocks.mapper.RoleMapper;
import com.bintoufha.gestionStocks.model.Permission;
import com.bintoufha.gestionStocks.repository.PermissionRepository;
import com.bintoufha.gestionStocks.services.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionSaveDto save(PermissionSaveDto permissionDto) {
        // Vérifier unicité
        if (permissionRepository.existsByCode(permissionDto.getCode())) {
            throw new RuntimeException("Une permission avec ce code existe déjà");
        }

        // Convertir DTO -> Entité
        Permission permission = PermissionMapper.toEntity(permissionDto);

        // Sauvegarder
        Permission savedPermssion = permissionRepository.save(permission);

        // Convertir Entité -> DTO
        return PermissionMapper.fromEntity(savedPermssion);
    }

    @Override
    public PermissionListDto getPermission(UUID uuid) {
        if (uuid == null) {
            log.error("role ID is null");
            return null;
        }
        return permissionRepository.findByUuid(uuid)
                .map(PermissionMapper::toListDto)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun permission avec l'ID = " + uuid + " n' ete trouve dans la BDD",
                        ErrorCodes.PERMISSION_NOT_FOUND)
                );
    }

    @Override
    public List<PermissionListDto> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permission -> {
                    // ou via TarificationService si besoin
                    return PermissionMapper.toListDto(permission);
                })
                .toList();
    }

    @Override
    public List<PermissionListDto> getPermissionsByModule(String module) {
        return permissionRepository.findByModule(module)
                .stream()
                .map(modules -> {
                    // ou via TarificationService si besoin
                    return PermissionMapper.toListDto(modules);
                })
                .toList();
    }

    @Override
    public List<PermissionListDto> getPermissionsByCategory(String category) {
        return permissionRepository.findByCategory(category)
                .stream()
                .map(categorie -> {
                    // ou via TarificationService si besoin
                    return PermissionMapper.toListDto(categorie);
                })
                .toList();
    }

    @Override
    public List<PermissionListDto> getPermissionsByModuleAndCategory(String module, String category) {
        return permissionRepository.findByModuleAndCategory(module,category)
                .stream()
                .map(Modulecategorie -> {
                    // ou via TarificationService si besoin
                    return PermissionMapper.toListDto(Modulecategorie);
                })
                .toList();
    }

    @Override
    public List<PermissionListDto> getPermissionsByRole(String roleName) {
        return permissionRepository.findByRoleName(roleName)
                .stream()
                .map(roles -> {
                    // ou via TarificationService si besoin
                    return PermissionMapper.toListDto(roles);
                })
                .toList();
    }

    @Override
    public void deletePermission(UUID uuid) {
        Permission permission = permissionRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Permission non trouvée"));

        // Vérifier si utilisée par des rôles
        if (!permission.getRoles().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : cette permission est utilisée par des rôles");
        }
        permissionRepository.delete(permission);
    }

    @Override
    public void initializeDefaultPermissions() {
        String[][] defaultPermissions = {
                // Module, Catégorie, Code, Nom, Description
                {"USER", "MANAGEMENT", "USER_CREATE", "Créer utilisateur", "Permet de créer un nouvel utilisateur"},
                {"USER", "MANAGEMENT", "USER_READ", "Lire utilisateur", "Permet de voir les informations utilisateur"},
                {"USER", "MANAGEMENT", "USER_UPDATE", "Modifier utilisateur", "Permet de modifier un utilisateur"},
                {"USER", "MANAGEMENT", "USER_DELETE", "Supprimer utilisateur", "Permet de supprimer un utilisateur"},

                {"BOUTIQUE", "MANAGEMENT", "BOUTIQUE_CREATE", "Créer boutique", "Permet de créer une nouvelle boutique"},
                {"BOUTIQUE", "MANAGEMENT", "BOUTIQUE_READ", "Lire boutique", "Permet de voir les informations boutique"},
                {"BOUTIQUE", "MANAGEMENT", "BOUTIQUE_UPDATE", "Modifier boutique", "Permet de modifier une boutique"},
                {"BOUTIQUE", "MANAGEMENT", "BOUTIQUE_DELETE", "Supprimer boutique", "Permet de supprimer une boutique"},

                {"STOCK", "MANAGEMENT", "STOCK_CREATE", "Créer stock", "Permet de créer une entrée de stock"},
                {"STOCK", "MANAGEMENT", "STOCK_READ", "Lire stock", "Permet de voir le stock"},
                {"STOCK", "MANAGEMENT", "STOCK_UPDATE", "Modifier stock", "Permet de modifier le stock"},
                {"STOCK", "MANAGEMENT", "STOCK_DELETE", "Supprimer stock", "Permet de supprimer une entrée de stock"},

                {"PRICE", "MANAGEMENT", "PRICE_CONFIGURE", "Configurer prix", "Permet de configurer les prix"},
                {"PRICE", "MANAGEMENT", "PRICE_READ", "Lire prix", "Permet de voir les prix"},
                {"PRICE", "MANAGEMENT", "PRICE_UPDATE", "Modifier prix", "Permet de modifier les prix"},

                {"ORDER", "MANAGEMENT", "ORDER_CREATE", "Créer commande", "Permet de créer une commande"},
                {"ORDER", "MANAGEMENT", "ORDER_READ", "Lire commande", "Permet de voir les commandes"},
                {"ORDER", "MANAGEMENT", "ORDER_UPDATE", "Modifier commande", "Permet de modifier une commande"},
                {"ORDER", "MANAGEMENT", "ORDER_DELETE", "Supprimer commande", "Permet de supprimer une commande"},

                {"REPORT", "VIEW", "REPORT_VIEW", "Voir rapports", "Permet de voir les rapports"},
                {"REPORT", "GENERATE", "REPORT_GENERATE", "Générer rapports", "Permet de générer des rapports"},

                {"CATEGORY", "MANAGEMENT", "CATEGORY_CREATE", "Créer catégorie", "Permet de créer une catégorie"},
                {"CATEGORY", "MANAGEMENT", "CATEGORY_READ", "Lire catégorie", "Permet de voir les catégories"},
                {"CATEGORY", "MANAGEMENT", "CATEGORY_UPDATE", "Modifier catégorie", "Permet de modifier une catégorie"},
                {"CATEGORY", "MANAGEMENT", "CATEGORY_DELETE", "Supprimer catégorie", "Permet de supprimer une catégorie"},

                {"ROLE", "MANAGEMENT", "ROLE_CREATE", "Créer rôle", "Permet de créer un rôle"},
                {"ROLE", "MANAGEMENT", "ROLE_READ", "Lire rôle", "Permet de voir les rôles"},
                {"ROLE", "MANAGEMENT", "ROLE_UPDATE", "Modifier rôle", "Permet de modifier un rôle"},
                {"ROLE", "MANAGEMENT", "ROLE_DELETE", "Supprimer rôle", "Permet de supprimer un rôle"},

                {"PERMISSION", "MANAGEMENT", "PERMISSION_CREATE", "Créer permission", "Permet de créer une permission"},
                {"PERMISSION", "MANAGEMENT", "PERMISSION_READ", "Lire permission", "Permet de voir les permissions"},
                {"PERMISSION", "MANAGEMENT", "PERMISSION_UPDATE", "Modifier permission", "Permet de modifier une permission"},
                {"PERMISSION", "MANAGEMENT", "PERMISSION_DELETE", "Supprimer permission", "Permet de supprimer une permission"}
        };

        for (String[] permData : defaultPermissions) {
            String module = permData[0];
            String category = permData[1];
            String code = permData[2];
            String name = permData[3];
            String description = permData[4];

            if (!permissionRepository.existsByCode(code)) {
                Permission permission = new Permission();
                permission.setCode(code);
                permission.setName(name);
                permission.setDescription(description);
                permission.setModule(module);
                permission.setCategory(category);
                permission.setAction(getActionFromCode(code));

                permissionRepository.save(permission);
            }
        }
    }

    private String getActionFromCode(String code) {
        if (code.endsWith("_CREATE")) return "CREATE";
        if (code.endsWith("_READ")) return "READ";
        if (code.endsWith("_UPDATE")) return "UPDATE";
        if (code.endsWith("_DELETE")) return "DELETE";
        if (code.contains("VIEW")) return "READ";
        if (code.contains("GENERATE")) return "CREATE";
        if (code.contains("CONFIGURE")) return "UPDATE";
        return "READ";
    }
}
