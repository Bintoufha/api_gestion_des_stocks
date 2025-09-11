package com.bintoufha.gestionStocks.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ventes")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Ventes extends AbstractEntity {

    @Column(name = "reference")
    private String reference;

    @Column(name = "typeVente")
    private String typeVente;

    @Column(name = "montantPayer")
    private BigDecimal montantPayer;

    @Column(name = "montantReste")
    private BigDecimal montantReste;

    @Column(name = "montantTotal")
    private BigDecimal montantTotal;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVentes;
}
