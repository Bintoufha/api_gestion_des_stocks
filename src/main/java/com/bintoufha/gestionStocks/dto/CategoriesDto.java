package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Categories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CategoriesDto {
    private UUID uuid;

    private String code;

    private String designation;

    private EntrepriseDto uuidEntreprise;
    @JsonIgnore
    private List<ArticlesDto> listArticle;

    public static CategoriesDto fromEntity(Categories categorie) {
        if (categorie == null) {
            return null;
            // TODO throw on exception
        }
// mapping de Categorie vers CategorieDTO
        return CategoriesDto.builder()
                .code(categorie.getCode())
                .designation(categorie.getDesignation())
               // .uuidEntreprise(categorie.getIdEntreprise())
                .build();
    }

    // mapping de CategorieDTO vers Categorie
    public static Categories toEntity(CategoriesDto categoriesDto) {
        if (categoriesDto == null) {
            return null;
            // TODO throw on exception
        }
        Categories categories = new Categories();
        categories.setCode(categoriesDto.getCode());
        categories.setDesignation(categoriesDto.getDesignation());
        categories.setUuid(categoriesDto.getUuid());
        return categories;
    }
}
