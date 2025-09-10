package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mvtStocks")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class MouvementStocks extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "uuidArticle")
    private Articles articles;

    @Column(name = "datemvt")
    private Instant dateMvt;

    @Column(name = "typeMouvement")
    private String typeMouvement;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @Column(name = "motif")
    private String motif;

}
