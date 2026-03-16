package com.bintoufha.gestionStocks.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role_entreprise",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"utilisateurs_uuid", "role_uuid", "entreprise_uuid"}
        ))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntreprise extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateurs_uuid", nullable = false)
    private Utilisateurs utilisateurs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_uuid", nullable = false)
    private Roles role;

    // NULL pour les rôles globaux, sinon restriction à une boutique
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_uuid")
    private Entreprises entreprises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private Utilisateurs assignedBy;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "active")
    private boolean active = true;


    // Méthode pour vérifier si l'assignation est valide
    public boolean isValid() {
        if (!active) {
            return false;
        }

        if (expiresAt != null && LocalDateTime.now().isAfter(expiresAt)) {
            return false;
        }

        return true;
    }
}
