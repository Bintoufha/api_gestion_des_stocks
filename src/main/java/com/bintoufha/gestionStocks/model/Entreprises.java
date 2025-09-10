package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "entreprise")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Entreprises extends AbstractEntity{

    @Column(name = "nomEntreprise")
    private String nomEntreprise;

    @Column(name = "description")
    private String description;

    @Embedded
    //@OneToOne(cascade = CascadeType.ALL)
    private Addresse addresse;

    @Column(name = "codeFiscale")
    private String codeFiscale;

    @Column(name = "image")
    private String photoEntreprise;

    @Column(name = "numTelephone")
    private String numero;

    @Column(name = "email")
    private String email;

    @Column(name = "siteWeb")
    private String siteWebUrl;

    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateurs> utilisateurs;


}
