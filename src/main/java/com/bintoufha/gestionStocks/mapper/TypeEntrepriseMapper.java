package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.article.ArticleListDto;
import com.bintoufha.gestionStocks.dto.categorie.CategorieInfoDto;
import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseListDto;
import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.TypeEntreprises;

import java.math.BigDecimal;

public class TypeEntrepriseMapper {

    public static TypeEntreprises toEntity(TypeEntrepriseSaveDto dto) {
        TypeEntreprises typeEntreprises = new TypeEntreprises();
        typeEntreprises.setNomTypeEntreprise(dto.getNomTypeEntreprise());
        typeEntreprises.setDescription(dto.getDescription());
        typeEntreprises.setCode(dto.getCode());
        return typeEntreprises;
    }

    public static TypeEntrepriseSaveDto fromEntity(TypeEntreprises typeEntreprises) {
        if (typeEntreprises == null) return null;

        return TypeEntrepriseSaveDto.builder()
                .nomTypeEntreprise(typeEntreprises.getNomTypeEntreprise())
                .description(typeEntreprises.getCode())
                .code(typeEntreprises.getCode())
                .build();

    }
    public static TypeEntrepriseListDto toListDto(TypeEntreprises typeEntreprises) {
        if (typeEntreprises == null) return null;

        return TypeEntrepriseListDto.builder()
                .uuid(typeEntreprises.getUuid())
                .code(typeEntreprises.getCode())
                .nomTypeEntreprise(typeEntreprises.getNomTypeEntreprise())
                .build();
    }
}
