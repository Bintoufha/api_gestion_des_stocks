package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.repository.UserRoleBoutiqueRepository;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.AuthorizationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRoleBoutiqueRepository userRoleBoutiqueRepository;
    private final UtilisateursRepository utilisateursRepository;
    private final EntrepriseRepository entrepriseRepository;

    public AuthorizationServiceImpl(UserRoleBoutiqueRepository userRoleBoutiqueRepository, UtilisateursRepository utilisateursRepository, EntrepriseRepository entrepriseRepository) {
        this.userRoleBoutiqueRepository = userRoleBoutiqueRepository;
        this.utilisateursRepository = utilisateursRepository;
        this.entrepriseRepository = entrepriseRepository;
    }


    @Override
    public List<UUID> getUserAccessibleBoutiques(UUID utilisateurUuid) {
        List<UserRoleEntreprise> userAssignments = userRoleBoutiqueRepository
                .findActiveByUtilisateursUuid(utilisateurUuid);

        return userAssignments.stream()
                .map(UserRoleEntreprise::getEntreprises)
                .filter(boutique -> boutique != null)
                .map(Entreprises::getUuid)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasPermission(UUID utilisateurUuid, String permissionCode, UUID entrepriseUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer toutes les assignations actives de l'utilisateur
        List<UserRoleEntreprise> userAssignments = userRoleBoutiqueRepository
                .findActiveByUtilisateursUuid(utilisateurUuid);

        // Pour chaque assignation, vérifier les permissions
        for (UserRoleEntreprise assignment : userAssignments) {
            Roles role = assignment.getRole();

            // Vérifier si le rôle a la permission
            if (role.hasPermission(permissionCode)) {
                // Vérifier le scope du rôle
                switch (role.getScope()) {
                    case GLOBAL:
                        // Rôle global: accès à tout
                        return true;

                    case ENTREPRISES:
                        // Rôle boutique: vérifier que la boutique correspond
                        if (assignment.getEntreprises() != null &&
                                assignment.getEntreprises().getUuid().equals(entrepriseUuid)) {
                            return true;
                        }
                        break;

                    case REGIONAL:
                        // Rôle régional: logique spécifique (à implémenter)
                        // Pour l'exemple, on suppose qu'on vérifie la région
                        if (isInSameRegion(assignment, entrepriseUuid)) {
                            return true;
                        }
                        break;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasGlobalPermission(UUID utilisateurUuid, String permissionCode) {
        List<UserRoleEntreprise> userAssignments = userRoleBoutiqueRepository
                .findActiveByUtilisateursUuid(utilisateurUuid);

        return userAssignments.stream()
                .filter(urb -> urb.getRole().getScope() == RoleScope.GLOBAL)
                .anyMatch(urb -> urb.getRole().hasPermission(permissionCode));
    }

    @Override
    public boolean hasBoutiquePermission(UUID utilisateurUuid, String permissionCode, UUID entrepriseUuid) {
        if (entrepriseUuid == null) {
            return false;
        }

        List<UserRoleEntreprise> userAssignments = userRoleBoutiqueRepository
                .findActiveByUtilisateursUuid(utilisateurUuid);

        return userAssignments.stream()
                .filter(urb -> urb.getEntreprises() != null &&
                        urb.getEntreprises().getUuid().equals(entrepriseUuid))
                .anyMatch(urb -> urb.getRole().hasPermission(permissionCode));
    }

    @Override
    public boolean canAccessBoutique(UUID utilisateurUuid, UUID entrepriseUuid) {
        // Si l'utilisateur a un rôle global, il peut accéder à tout
        if (hasGlobalRole(utilisateurUuid)) {
            return true;
        }

        // Sinon, vérifier s'il a accès à cette boutique spécifique
        return getUserAccessibleBoutiques(utilisateurUuid).contains(entrepriseUuid);
    }

    @Override
    public boolean hasGlobalRole(UUID utilisateurUuid) {
        List<UserRoleEntreprise> userAssignments = userRoleBoutiqueRepository
                .findActiveByUtilisateursUuid(utilisateurUuid);

        return userAssignments.stream()
                .anyMatch(urb -> urb.getRole().getScope() == RoleScope.GLOBAL);
    }

    @Override
    public boolean isInSameRegion(UserRoleEntreprise assignment, UUID entrepriseUuid) {
        // Implémentation simplifiée: on suppose que la région est stockée dans la boutique
        // Dans une vraie implémentation, vous auriez une entité Région
        Entreprises targetBoutique = entrepriseRepository.findByUuid(entrepriseUuid)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));

        // Pour l'exemple, on compare les villes
        return assignment.getEntreprises().getAddresse().getVille().equals(targetBoutique.getAddresse().getVille());
    }

    @Override
    public boolean canAdjustStock(UUID utilisateurUuid, Integer quantity, UUID entrepriseUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 1. Vérifier la permission RBAC
        if (!hasPermission(utilisateurUuid, "STOCK_UPDATE", entrepriseUuid)) {
            return false;
        }

        // 2. Vérifier les attributs (ABAC léger)
        if (user.getStockAdjustmentLimit() != null &&
                Math.abs(quantity) > user.getStockAdjustmentLimit()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canApplyDiscount(UUID utilisateurUuid, Double discountPercentage, UUID entrepriseUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 1. Vérifier la permission RBAC
        if (!hasPermission(utilisateurUuid, "PRICE_UPDATE", entrepriseUuid)) {
            return false;
        }

        // 2. Vérifier les attributs (ABAC léger)
        if (user.getMaxDiscount() != null &&
                BigDecimal.valueOf(discountPercentage).compareTo(user.getMaxDiscount()) > 0) {
            return false;
        }

        return true;
    }
}
