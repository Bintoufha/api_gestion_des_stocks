package com.bintoufha.gestionStocks.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "type_entreprise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeEntreprises extends AbstractEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String nomTypeEntreprise;

    @Column(length = 500)
    private String description;

    @Column(name = "code", unique = true, length = 50)
    private String code;

    @OneToMany(mappedBy = "typeEntreprises", fetch = FetchType.LAZY)
    private List<Entreprises> entreprises = new ArrayList<>();

}
