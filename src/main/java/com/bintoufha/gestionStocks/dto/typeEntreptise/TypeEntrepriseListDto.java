package com.bintoufha.gestionStocks.dto.typeEntreptise;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TypeEntrepriseListDto {

    private UUID uuid;

    private String nomTypeEntreprise;

    private String code;

}
