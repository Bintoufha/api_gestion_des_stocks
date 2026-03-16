package com.bintoufha.gestionStocks.dto.ligneVente;

import java.math.BigDecimal;
import java.util.UUID;

import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneVenteDto {

    private UUID uuid;

    private VentesDto vente;


    private ArticleSaveDto articles;

    private BigDecimal qte;

    private UUID idEntreprise;


    private BigDecimal prix;
}