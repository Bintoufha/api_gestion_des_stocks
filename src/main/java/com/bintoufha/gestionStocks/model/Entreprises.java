package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;


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

    @Embedded
    //@OneToOne(cascade = CascadeType.ALL)
    private Addresse addresse;

    private String email;

//    private Addresse addresse;
//
//    private Addresse addresse;
//
//    private Addresse addresse;


}
