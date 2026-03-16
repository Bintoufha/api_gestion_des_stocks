package com.bintoufha.gestionStocks.dto.commandeClient;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class CommandeClientListDto {
    private UUID uuid;
    private String reference;
    private Instant dateCommande;
    private Clients clients;
    private EtatCommande etatCommande;

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}

