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
public class EntrepriseConfigSaveDto {

    private BigDecimal margeGros; // Pourcentage

    private BigDecimal margeDetail; // Pourcentage

    private Entreprises entreprise;

    private Categories categorie;

    private Utilisateurs configurePar;



}
