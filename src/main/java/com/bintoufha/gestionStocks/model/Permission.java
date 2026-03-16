package com.bintoufha.gestionStocks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AbstractEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    // Module auquel appartient cette permission
    @Column(length = 50)
    private String module;

    // Catégorie pour regrouper les permissions
    @Column(length = 50)
    private String category;

    // Actions possibles (CRUD)
    @Column(length = 10)
    private String action; // CREATE, READ, UPDATE, DELETE

    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles = new HashSet<>();


}
