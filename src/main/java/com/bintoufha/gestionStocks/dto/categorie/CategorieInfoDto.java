package com.bintoufha.gestionStocks.dto.categorie;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategorieInfoDto {
    private UUID uuid;
    private String designation;
    private Double detaillePourcent;
    private Double engrosPourcent;
}
