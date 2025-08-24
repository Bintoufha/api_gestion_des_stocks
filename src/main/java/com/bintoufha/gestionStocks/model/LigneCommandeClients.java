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
@Table(name = "ligneClients")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class LigneCommandeClients extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "uuidArticle")
    private Articles articles;

    @ManyToOne
    @JoinColumn(name = "uuidCommandeClients")
    private CommandeClients commandeClients;

    @JoinColumn(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @JoinColumn(name = "prixUnitaire")
    private  BigDecimal prixUnitaire;
}
