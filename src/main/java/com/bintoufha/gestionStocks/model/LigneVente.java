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
@Table(name = "ligneVente")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class LigneVente extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "uuidVente")
    private Ventes vente;

    @ManyToOne
    @JoinColumn(name = "uuidArticle")
    private Articles articles;

    @Column(name = "quantite")
    private BigDecimal Qte;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @Column(name = "prixEngrosDetaillant")
    private BigDecimal prix;
}
