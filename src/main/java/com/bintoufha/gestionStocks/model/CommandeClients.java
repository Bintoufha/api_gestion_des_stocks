package com.bintoufha.gestionStocks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandeClients")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class CommandeClients extends AbstractEntity {

    @Column(name = "reference")
    private String refernce;

    @Column(name = "dateCommande")
    private Instant dateCommande;

    @Column(name = "uuidEntreprise")
    private UUID idEntreprise;

    @Column(name = "etatcommande")
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "uuidClient")
    private Clients clients;

    @OneToMany(mappedBy = "commandeClients")
    private List<LigneCommandeClients> commandeClient;
}
