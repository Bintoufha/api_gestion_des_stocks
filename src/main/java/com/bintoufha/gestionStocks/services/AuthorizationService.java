package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.model.UserRoleEntreprise;
import com.bintoufha.gestionStocks.repository.UserRoleBoutiqueRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorizationService {

    List<UUID> getUserAccessibleBoutiques(UUID utilisateurUuid);

    boolean hasPermission(UUID utilisateurUuid, String permissionCode, UUID entrepriseUuid);

    boolean hasGlobalPermission(UUID utilisateurUuid, String permissionCode);

    boolean hasBoutiquePermission(UUID utilisateurUuid, String permissionCode, UUID entrepriseUuid);

    boolean canAccessBoutique(UUID utilisateurUuid, UUID entrepriseUuid);

    boolean hasGlobalRole(UUID utilisateurUuid);

    boolean isInSameRegion(UserRoleEntreprise assignment, UUID entrepriseUuid);

    boolean canAdjustStock(UUID utilisateurUuid, Integer quantity, UUID entrepriseUuid);

    boolean canApplyDiscount(UUID utilisateurUuid, Double discountPercentage, UUID entrepriseUuid);


}
