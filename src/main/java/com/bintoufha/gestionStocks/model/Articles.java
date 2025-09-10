package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Articles extends AbstractEntity {

    @Column(name = "nomArticle")
    private String nomArticle;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaireArticle;

    @Column(name = "prixEngros")
    private BigDecimal prixEngrosArticle;

    @Column(name = "prixDetaille")
    private BigDecimal prixDetailleArticle;

    @Column(name = "seuil")
    private BigDecimal seuilRuptureArticle;

    @Column(name = "qteStocks")
    private BigDecimal quantieStocksArticle;

    @Column(name = "image")
    private String photoArticle;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @ManyToOne
    @JoinColumn(name = "uuiCategorie")
    private Categories categorie;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneVente> ligneVentes;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeClients> ligneCommandeClients;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeFournisseurs> ligneCommandeFournisseurs;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MouvementStocks> mouvementStocks;

}
