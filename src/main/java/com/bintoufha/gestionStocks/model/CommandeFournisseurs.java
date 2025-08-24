package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
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
    private String Refernce;

    @Column(name = "dateCommande")
    private Instant DateCommande;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @ManyToOne
    @JoinColumn(name = "uuidFournisseurs")
    private Fournisseurs fournisseurs;

    @OneToMany(mappedBy = "commandefournisseur")
    private List<LigneCommandeFournisseurs> LigneCommandeFournisseur;
}
