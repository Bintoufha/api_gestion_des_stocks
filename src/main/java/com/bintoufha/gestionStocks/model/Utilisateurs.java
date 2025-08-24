package com.bintoufha.gestionStocks.model;

import com.bintoufha.gestionStocks.dto.AddresseDto;
import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import com.bintoufha.gestionStocks.dto.RolesDto;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "utilisateurs")  // optionnelle si c'est pas definis il prendra le nom de la classe
public class Utilisateurs extends AbstractEntity {

    private UUID uuid;

    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    private String emailUtilisateurs;

    private String pwd;

    private String telephoneUtilisateurs;

    private Addresse addresse;

    @ManyToOne
    @JoinColumn(name = "uuidEntreprise")
    private Entreprises entreprise;

    private String photoUtilisateurs;

    private List<Roles> roles;
}
