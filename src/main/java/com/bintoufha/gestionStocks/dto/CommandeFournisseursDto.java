package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
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

    private String Refernce;

    private Instant DateCommande;


    private Fournisseurs fournisseurs;

    private UUID idEntreprise;


    private List<LigneCommandeFournisseursDto> LigneCommandeFournisseur;

    public static CommandeFournisseursDto fromEntity(CommandeFournisseurs commandeFournisseurs){
        if (commandeFournisseurs == null){
            return null;
        }
        return CommandeFournisseursDto.builder()
                .uuid(commandeFournisseurs.getUuid())
                .Refernce(commandeFournisseurs.getRefernce())
                .DateCommande(commandeFournisseurs.getDateCommande())
                .fournisseurs(commandeFournisseurs.getFournisseurs())
                .idEntreprise(commandeFournisseurs.getIdEntreprise())
                .build();
    }
    public static CommandeFournisseurs toEntity (CommandeFournisseursDto commandeFournisseursDto){
        if (commandeFournisseursDto == null){
            return null;
        }
        CommandeFournisseurs commandeFournisseurs = new CommandeFournisseurs();
        commandeFournisseurs.setUuid(commandeFournisseursDto.getUuid());
        commandeFournisseurs.setRefernce(commandeFournisseursDto.getRefernce());
        commandeFournisseurs.setIdEntreprise(commandeFournisseursDto.getIdEntreprise());
        commandeFournisseurs.setDateCommande(commandeFournisseursDto.getDateCommande());
        return commandeFournisseurs;
    }
}
