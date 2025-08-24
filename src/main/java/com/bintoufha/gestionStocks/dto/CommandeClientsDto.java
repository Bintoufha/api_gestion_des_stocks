package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;
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

    private Instant DateCommande;

    private Clients clients;

    private UUID idEntreprise;

    public List<LigneCommandeClientsDto> ligneCommandeClients;

    public static CommandeClientsDto fromEntity(CommandeClients commandeClients){
        if (commandeClients == null){
            return null;
        }
        return CommandeClientsDto.builder()
                .uuid(commandeClients.getUuid())
                .Refernce(commandeClients.getRefernce())
                .DateCommande(commandeClients.getDateCommande())
                .clients(commandeClients.getClients())
                .idEntreprise(commandeClients.getIdEntreprise())
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
        commandeClients.setDateCommande(commandeClientsDto.getDateCommande());
        return commandeClients;
    }
}
