package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lotStocks")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class LotStocks extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "uuidArticle")    
    private Articles articles;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @Column(name = "quantite")
    private BigDecimal qteCommande;

    @Column(name = "quantiteRestant")
    private BigDecimal qteRestant;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;

//    @Column(name = "uuidEntreprise")
//    private Date dateIn;
}
