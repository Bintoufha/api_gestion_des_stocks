package com.bintoufha.gestionStocks.dto.categorie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorieSaveDto {
    private String code;
    private String designation;
    private Double detaillePourcent;
    private Double engrosPourcent;
}
