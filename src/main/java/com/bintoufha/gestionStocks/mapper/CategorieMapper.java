package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.categorie.*;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Categories;

public class CategorieMapper {

    public static CategorieListDto toListDto(Categories categorie) {
        if (categorie == null) return null;

        return CategorieListDto.builder()
                .uuid(categorie.getUuid())
                .code(categorie.getCode())
                .designation(categorie.getDesignation())
                .build();
    }

    public static Categories toEntity(CategorieSaveDto dto) {
        if (dto == null) return null;

        Categories categorie = new Categories();
        categorie.setCode(dto.getCode());
        categorie.setDesignation(dto.getDesignation());

        return categorie;
    }

    public static void updateEntity(Categories categorie, CategorieSaveDto dto) {
        categorie.setCode(dto.getCode());
        categorie.setDesignation(dto.getDesignation());

    }
    // Entity → DTO (pour save / update)
    public static CategorieSaveDto toSaveDto(Categories categorie) {
        if (categorie == null) return null;

        return CategorieSaveDto.builder()
                .code(categorie.getCode())
                .designation(categorie.getDesignation())

                .build();
    }
}
