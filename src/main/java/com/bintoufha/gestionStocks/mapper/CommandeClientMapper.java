package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientListDto;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.CommandeClients;

import java.util.UUID;

public class CommandeClientMapper {

    public static CommandeClients toEntity(CommandeClientSaveDto dto, Clients client) {

        CommandeClients cmd = new CommandeClients();
       // cmd.setUuid(dto.getUuid());
        cmd.setClients(client);
        cmd.setRefernce(dto.getRefernce());
        cmd.setEtatCommande(dto.getEtatCommande());
        cmd.setDateCommande(dto.getDateCommande());
        cmd.setIdEntreprise(dto.getIdEntreprise());
        return cmd;
    }

    public static CommandeClientListDto toListDto(CommandeClients cmd) {
        return CommandeClientListDto.builder()
                .uuid(cmd.getUuid())
                .reference(cmd.getRefernce())
                .dateCommande(cmd.getDateCommande())
                .etatCommande(cmd.getEtatCommande())
                .build();
    }


    public static CommandeClientSaveDto fromEntity(CommandeClients saveCmdClt) {
        if (saveCmdClt == null) return null;

        return CommandeClientSaveDto.builder()
                .refernce(saveCmdClt.getRefernce())
                .dateCommande(saveCmdClt.getDateCommande())
                .clients(saveCmdClt.getClients().getUuid())
                .etatCommande(saveCmdClt.getEtatCommande())
                .idEntreprise(saveCmdClt.getIdEntreprise())
                .build();
    }
}
