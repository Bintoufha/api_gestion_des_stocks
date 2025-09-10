package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder

public class CommandeClientsDto {
   // public boolean getLigneCommandeClient;
    private UUID uuid;
    private String Refernce;

    private Instant dateCommande;

    private ClientsDto clients;

    private UUID idEntreprise;

    private EtatCommande etatCommande;
    @JsonIgnore
    public List<LigneCommandeClientsDto> ligneCommandeClients;

    public static CommandeClientsDto fromEntity(CommandeClients commandeClients){
        if (commandeClients == null){
            return null;
        }
        return CommandeClientsDto.builder()
                .uuid(commandeClients.getUuid())
                .Refernce(commandeClients.getRefernce())
                .dateCommande(commandeClients.getDateCommande())
                .clients(ClientsDto.fromEntity(commandeClients.getClients()))
                .idEntreprise(commandeClients.getIdEntreprise())
                .etatCommande(commandeClients.getEtatCommande())
                .build();
    }
    public static CommandeClients toEntity (CommandeClientsDto commandeClientsDto){
        if (commandeClientsDto == null){
            return null;
        }
        CommandeClients commandeClients = new CommandeClients();
        commandeClients.setUuid(commandeClientsDto.getUuid());
        commandeClients.setRefernce(commandeClientsDto.getRefernce());
        commandeClients.setIdEntreprise(commandeClientsDto.getIdEntreprise());
        commandeClients.setClients(ClientsDto.toEntity(commandeClientsDto.getClients()));
        commandeClients.setEtatCommande(commandeClientsDto.getEtatCommande());
        commandeClients.setDateCommande(commandeClientsDto.getDateCommande());
        return commandeClients;
    }



    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
