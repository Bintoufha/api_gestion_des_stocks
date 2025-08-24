package com.bintoufha.gestionStocks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categorie")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Categories  extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "designation")
    private String designation;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @OneToMany(mappedBy = "categorie")
    private List<Articles> listArticle;
    
}
