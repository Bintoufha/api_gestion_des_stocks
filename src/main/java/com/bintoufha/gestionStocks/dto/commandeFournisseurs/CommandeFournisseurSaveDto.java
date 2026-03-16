package com.bintoufha.gestionStocks.dto.commandeFournisseurs;

import com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs.LigneCommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CommandeFournisseurSaveDto {
    private String reference;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private UUID idEntreprise;

    private UUID fournisseursUuid; // ⚠️ utiliser UUID et non entité pour le save

    private List<LigneCommandeFournisseurSaveDto> ligneCommandeFournisseurs;

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
