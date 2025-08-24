package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignefournisseurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class LigneCommandeFournisseurs extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "uuidArticle")
    private Articles articles;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @ManyToOne
    @JoinColumn(name = "uuidCommandeFournisseurs")
    private CommandeFournisseurs commandefournisseur;
}
