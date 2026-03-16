package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Roles extends AbstractEntity {

    @Column(name = "role")
    private String nomRole;

    @Column(name = "description")
    private String description;

    // Hiérarchie des rôles
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_role_id")
    private Roles parentRole;

    @OneToMany(mappedBy = "parentRole")
    private Set<Roles> childRoles = new HashSet<>();

    // Scope du rôle (Global ou Boutique)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleScope scope = RoleScope.GLOBAL;

    // RBAC: Permissions associées à ce rôle
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "uuidUtilisateur")
    private Utilisateurs utilisateurs;

    // Méthode pour vérifier si ce rôle a une permission
    public boolean hasPermission(String permissionCode) {
        return permissions.stream()
                .anyMatch(permission -> permission.getCode().equals(permissionCode));
    }

    // Méthode pour hériter des permissions du parent
    public Set<Permission> getAllPermissions() {
        Set<Permission> allPermissions = new HashSet<>(permissions);

        if (parentRole != null) {
            allPermissions.addAll(parentRole.getAllPermissions());
        }

        return allPermissions;
    }
}
