package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.math.BigDecimal;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandeFournisseurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class CommandeFournisseurs extends AbstractEntity {

    @Column(name = "reference")
    private String reference;

    @Column(name = "dateCommande")
    private Instant dateCommande;
    
    // @Column(name = "montantPayer")
    // private BigDecimal montantPayer;

    // @Column(name = "montantReste")
    // private BigDecimal montantReste;

    // @Column(name = "montantTotal")
    // private BigDecimal montantTotal;

    @Column(name = "etatcommande")
    private EtatCommande etatCommande;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @ManyToOne
    @JoinColumn(name = "uuidFournisseurs")
    private Fournisseurs fournisseurs;

    @OneToMany(mappedBy = "commandefournisseur")
    private List<LigneCommandeFournisseurs> ligneCommandeFournisseur;
}
