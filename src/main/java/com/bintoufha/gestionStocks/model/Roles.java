package com.bintoufha.gestionStocks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
