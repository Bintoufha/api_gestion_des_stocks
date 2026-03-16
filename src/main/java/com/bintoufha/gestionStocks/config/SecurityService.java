package com.bintoufha.gestionStocks.config;

import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("securityService")
public class SecurityService {

    @Autowired
    private UtilisateursRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public boolean isMyBoutique(UUID entreprise_uuid) {
        ExtendedUser currentUser = getCurrentUser();

        // SuperAdmin a accès à tout
        if (currentUser.hasRole("SUPER_ADMIN") || currentUser.hasRole("ADMIN_GENERAL")) {
            return true;
        }

        // AdminBoutique: vérifier si c'est SA boutique
        if (currentUser.getIdEntreprise() != null &&
                currentUser.getIdEntreprise().equals(entreprise_uuid)) {
            return true;
        }

        return false;
    }

    public boolean isMyBoutiqueUser(UUID targetUserId) {
        ExtendedUser currentUser = getCurrentUser();
        Utilisateurs targetUser = userRepository.findByUuid(targetUserId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // SuperAdmin et AdminGeneral peuvent gérer tous les utilisateurs
        if (currentUser.hasRole("SUPER_ADMIN") || currentUser.hasRole("ADMIN_GENERAL")) {
            return true;
        }

        // AdminBoutique: vérifier si l'utilisateur cible appartient à SA boutique
        if (currentUser.getIdEntreprise() != null &&
                targetUser.getEntreprise() != null &&
                currentUser.getIdEntreprise().equals(targetUser.getEntreprise().getUuid())) {
            return true;
        }

        return false;
    }

    public boolean canAssignRoles(Long targetUserId, UUID entreprise_uuid) {
        ExtendedUser currentUser = getCurrentUser();

        // Vérifier les permissions RBAC
        return authorizationService.hasPermission(
                currentUser.getUuid(),
                "ROLE_ASSIGN",
                entreprise_uuid
        );
    }

    @Transactional
    public ExtendedUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ExtendedUser principal = (ExtendedUser) auth.getPrincipal();
        // Ici, on utilise directement l’UUID stocké dans ton ExtendedUser
        Utilisateurs user = userRepository.findByUuid(principal.getUuid())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ExtendedUser.create(user); // ⚡ conversion entité → ExtendedUser


    }

    public UUID getCurrentUserId() {
        return getCurrentUser().getUuid();
    }

    public UUID getCurrentUserBoutiqueId() {
        ExtendedUser user = getCurrentUser();
        return user.getIdEntreprise() != null ? user.getIdEntreprise() : null;
    }
}
