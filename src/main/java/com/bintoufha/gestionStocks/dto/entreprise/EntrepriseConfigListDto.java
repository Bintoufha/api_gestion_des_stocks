package com.bintoufha.gestionStocks.dto.entreprise;

import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@Data
public class EntrepriseConfigListDto {

    private UUID uuid;

    private boolean active;

    private BigDecimal margeGros; // Pourcentage

    private BigDecimal margeDetail; // Pourcentage

    private Entreprises Entreprise;

    private Categories categorie;

    private Utilisateurs configurePar;

    BigDecimal calculerPrixGros;

    BigDecimal calculerPrixDetail;
}
