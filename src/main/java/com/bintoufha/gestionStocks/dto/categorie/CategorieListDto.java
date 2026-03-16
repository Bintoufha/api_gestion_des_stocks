package com.bintoufha.gestionStocks.dto.categorie;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CategorieListDto {
    private UUID uuid;
    private String code;
    private String designation;
}
