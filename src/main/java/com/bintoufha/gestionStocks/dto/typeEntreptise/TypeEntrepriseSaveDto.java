package com.bintoufha.gestionStocks.dto.typeEntreptise;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TypeEntrepriseSaveDto {

    private String nomTypeEntreprise;

    private String description;

    private String code;

}
