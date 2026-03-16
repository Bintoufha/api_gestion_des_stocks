package com.bintoufha.gestionStocks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utilisateurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Utilisateurs extends AbstractEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    @Column(nullable = false, length = 100)
    private String email;

    private String pwd;

    @Column(name = "telephone", length = 20)
    private String telephoneUtilisateurs;

    // Attributs supplémentaires pour ABAC léger
    @Column(name = "max_discount", precision = 10, scale = 2)
    private BigDecimal maxDiscount = BigDecimal.valueOf(10); // Pourcentage max de remise

    @Column(name = "can_override_price")
    private Boolean canOverridePrice = false;

    @Column(name = "stock_adjustment_limit")
    private Integer stockAdjustmentLimit = 100;

    private Addresse addresse;

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "utilisateurs_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid")
    )
    private Set<Roles> roles = new HashSet<>();

    @Column(name = "actif")
    private boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_uuid")
    private Entreprises entreprise;

    private String photoUtilisateurs;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateurs")
    @JsonIgnore
    private List<Roles> role;



    // Méthodes utilitaires


    public boolean hasAnyRole(String... roleNames) {
        return roles.stream().anyMatch(role -> {
            for (String roleName : roleNames) {
                if (role.getNomRole().equals(roleName)) {
                    return true;
                }
            }
            return false;
        });
    }

    public boolean hasPermission(String permissionCode) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.getCode().equals(permissionCode));
    }

}
