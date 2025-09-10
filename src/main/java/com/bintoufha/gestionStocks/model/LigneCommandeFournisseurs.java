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
@Table(name = "lignefournisseurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class LigneCommandeFournisseurs extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "uuidArticle")
    private Articles articles;

    @JoinColumn(name = "quantite")
    private BigDecimal quantite;

    @JoinColumn(name = "prixUnitaire")
    private  BigDecimal prixUnitaire;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @ManyToOne
    @JoinColumn(name = "uuidCommandeFournisseurs")
    private CommandeFournisseurs commandefournisseur;
}
