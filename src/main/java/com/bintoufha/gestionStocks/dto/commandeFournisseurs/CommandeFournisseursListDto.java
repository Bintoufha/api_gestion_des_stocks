package com.bintoufha.gestionStocks.dto.commandeFournisseurs;
import com.bintoufha.gestionStocks.model.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class CommandeFournisseursListDto {
    private UUID uuid;
    private String reference;
    private Instant dateCommande;
    private EtatCommande etatCommande;

}
