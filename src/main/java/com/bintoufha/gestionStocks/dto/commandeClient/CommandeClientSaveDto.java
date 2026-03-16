package com.bintoufha.gestionStocks.dto.commandeClient;

import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CommandeClientSaveDto {

    private String refernce;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private UUID clients;

    private UUID idEntreprise;

    @JsonIgnore
    public List<LigneCommandeClientSaveDto> ligneCommandeClients;

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
