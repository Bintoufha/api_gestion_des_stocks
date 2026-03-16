package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.model.CommandeClients;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import com.bintoufha.gestionStocks.model.Fournisseurs;

public class CommandeFournisseurMapper {

    public static CommandeFournisseurs toEntity(CommandeFournisseurSaveDto dto, Fournisseurs fournisseurs) {
        CommandeFournisseurs cmd = new CommandeFournisseurs();
        cmd.setFournisseurs(fournisseurs);
        cmd.setDateCommande(dto.getDateCommande());
        cmd.setIdEntreprise(dto.getIdEntreprise());
        return cmd;
    }

    public static CommandeFournisseursListDto toListDto(CommandeFournisseurs cmd) {
        return CommandeFournisseursListDto.builder()
                .uuid(cmd.getUuid())
                .reference(cmd.getReference())
                .dateCommande(cmd.getDateCommande())
                .etatCommande(cmd.getEtatCommande())
                .build();
    }

    public static CommandeFournisseurSaveDto fromEntity(CommandeFournisseurs saveCmdClt) {
        if (saveCmdClt == null) return null;

        return CommandeFournisseurSaveDto.builder()
                .reference(saveCmdClt.getReference())
                .dateCommande(saveCmdClt.getDateCommande())
                .fournisseursUuid(saveCmdClt.getFournisseurs().getUuid())
                .etatCommande(saveCmdClt.getEtatCommande())
                .idEntreprise(saveCmdClt.getIdEntreprise())
                .build();
    }
}
