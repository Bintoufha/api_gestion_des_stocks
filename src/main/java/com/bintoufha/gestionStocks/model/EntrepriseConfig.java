package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tarification_entreprise",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"entreprise_uuid", "categorie_uuid"}
        )) // optionnelle si c'est pas definis il prendra le nom de la classe
public class EntrepriseConfig extends AbstractEntity {

    @Column(name = "methode")
    private String valorisationMethod; // FIFO/LIFO/PMP/HYBRIDE

    @Column(name = "marge_gros", precision = 5, scale = 2, nullable = false)
    private BigDecimal margeGros; // Pourcentage

    @Column(name = "marge_detail", precision = 5, scale = 2, nullable = false)
    private BigDecimal margeDetail; // Pourcentage


    @Column(name = "active" , nullable = false)
    private Boolean active=Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_uuid", nullable = false)
    private Entreprises entreprise;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_uuid", nullable = false)
    private Categories categorie;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_utilisateur")
    private Utilisateurs configurePar;

    // Méthode utilitaire pour calculer le prix
    public BigDecimal calculerPrixGros(BigDecimal prixAchat) {
        return prixAchat.multiply(
                BigDecimal.ONE.add(margeGros.divide(BigDecimal.valueOf(100)))
        );
    }

    public BigDecimal calculerPrixDetail(BigDecimal prixAchat) {
        return prixAchat.multiply(
                BigDecimal.ONE.add(margeDetail.divide(BigDecimal.valueOf(100)))
        );
    }
}
