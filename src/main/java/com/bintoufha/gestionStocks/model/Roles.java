package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Roles extends AbstractEntity {

    @Column(name = "role")
    private String nomRole;

    @ManyToOne
    @JoinColumn(name = "uuidUtilisateur")
    private Utilisateurs utilisateurs;
}
