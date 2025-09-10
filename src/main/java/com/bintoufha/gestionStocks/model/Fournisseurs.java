package com.bintoufha.gestionStocks.model;

import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fournisseurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Fournisseurs extends AbstractEntity{

    @Column(name = "nomPrenom")
    private String nomPrenomFournisseurs;

    @Column(name = "email")
    private String emailFournisseurs;

    @Column(name = "telephone")
    private String telephoneFournisseurs;

    @Embedded
    private Addresse addresse;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @Column(name = "image")
    private String photoFournisseurs;

    @OneToMany(mappedBy = "fournisseurs")
    private List<CommandeFournisseurs> commandeFournisseurs;
}
