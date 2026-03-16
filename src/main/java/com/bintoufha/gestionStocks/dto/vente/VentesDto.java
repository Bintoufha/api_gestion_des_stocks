package com.bintoufha.gestionStocks.dto.vente;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentesDto {
    private UUID uuid;

    private String reference;

    private String valorisationMethod;

    private Instant dateCommande;

    private UUID idEntreprise;

    private BigDecimal montantPayer;

    private BigDecimal montantReste;

    private BigDecimal montantTotal;

    private List<LigneVenteDto> ligneVentes;
}