package com.bintoufha.gestionStocks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Addresse addresse;


}
