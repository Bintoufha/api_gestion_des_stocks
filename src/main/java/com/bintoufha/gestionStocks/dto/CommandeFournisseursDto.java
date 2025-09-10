package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder

public class CommandeFournisseursDto {

    private UUID uuid;

    private String reference;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private FournisseursDto fournisseurs;

    private UUID idEntreprise;


    private List<LigneCommandeFournisseursDto> LigneCommandeFournisseur;

    public static CommandeFournisseursDto fromEntity(CommandeFournisseurs commandeFournisseurs){
        if (commandeFournisseurs == null){
            return null;
        }
        return CommandeFournisseursDto.builder()
                .uuid(commandeFournisseurs.getUuid())
                .reference(commandeFournisseurs.getReference())
                .dateCommande(commandeFournisseurs.getDateCommande())
                .fournisseurs(FournisseursDto.fromEntity(commandeFournisseurs.getFournisseurs()))
                .etatCommande(commandeFournisseurs.getEtatCommande())
                .idEntreprise(commandeFournisseurs.getIdEntreprise())
                .build();
    }
    public static CommandeFournisseurs toEntity (CommandeFournisseursDto commandeFournisseursDto){
        if (commandeFournisseursDto == null){
            return null;
        }
        CommandeFournisseurs commandeFournisseurs = new CommandeFournisseurs();
        commandeFournisseurs.setUuid(commandeFournisseursDto.getUuid());
        commandeFournisseurs.setReference(commandeFournisseursDto.getReference());
        commandeFournisseurs.setIdEntreprise(commandeFournisseursDto.getIdEntreprise());
        commandeFournisseurs.setEtatCommande(commandeFournisseursDto.getEtatCommande());
        commandeFournisseurs.setFournisseurs(FournisseursDto.toEntity(commandeFournisseursDto.getFournisseurs()));
        commandeFournisseurs.setDateCommande(commandeFournisseursDto.getDateCommande());
        return commandeFournisseurs;
    }


    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
