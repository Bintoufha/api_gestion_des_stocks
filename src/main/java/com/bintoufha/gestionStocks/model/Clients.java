package com.bintoufha.gestionStocks.model;

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
@Table(name = "clients")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Clients extends AbstractEntity {

    @Column(name = "nomPrenom")
    private String nomPrenomClient;

    @Column(name = "email")
    private String emailClient;

    @Column(name = "telephone")
    private String telephoneClient;

    @Embedded
    private Addresse addresse;

    @Column(name = "Image")
    private String photoClient;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @OneToMany(mappedBy = "clients")
    private List<CommandeClients> commandeClient;
}
