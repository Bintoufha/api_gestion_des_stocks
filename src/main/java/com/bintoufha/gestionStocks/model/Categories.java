package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categorie")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Categories extends AbstractEntity {

    
    @Column(name = "code")
    private String code;

    
    @Column(name = "designation")
    private String designation;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    private List<Articles> listArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_parent_id")
    private Categories categorieParent;

    @OneToMany(mappedBy = "categorieParent", fetch = FetchType.LAZY)
    private List<Categories> sousCategories = new ArrayList<>();

}
