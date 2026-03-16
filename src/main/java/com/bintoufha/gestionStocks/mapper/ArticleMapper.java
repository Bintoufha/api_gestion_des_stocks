package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.article.*;
import com.bintoufha.gestionStocks.dto.categorie.CategorieInfoDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Categories;

import java.math.BigDecimal;

public class ArticleMapper {

    public static ArticleListDto toListDto(Articles article, BigDecimal prix) {
        if (article == null) return null;

        return ArticleListDto.builder()
                .uuid(article.getUuid())
                .nomArticle(article.getNomArticle())
                .prixUnitaireArticle(article.getPrixUnitaireArticle())
                .categorie(
                        CategorieInfoDto.builder()
                                .uuid(article.getCategorie().getUuid())
                                .designation(article.getCategorie().getDesignation())
//                                .engrosPourcent(article.getCategorie().getEngrosPourcent())
//                                .detaillePourcent(article.getCategorie().getDetaillePourcent())
                                .build()
                )
                .build();
    }

    // DTO → Entity
    public static Articles toEntity(ArticleSaveDto dto, Categories categorie) {
        Articles article = new Articles();
        article.setNomArticle(dto.getNomArticle());
        article.setPrixUnitaireArticle(dto.getPrixUnitaireArticle());
        article.setSeuilRuptureArticle(dto.getSeuilRuptureArticle());
        article.setQuantieStocksArticle(dto.getQuantieStocksArticle());
        article.setCategorie(categorie);
        return article;
    }

    // Entity → DTO (pour save / update)
    public static ArticleSaveDto toSaveDto(Articles article) {
        if (article == null) return null;

        return ArticleSaveDto.builder()
                .nomArticle(article.getNomArticle())
                .prixUnitaireArticle(article.getPrixUnitaireArticle())
                .seuilRuptureArticle(article.getSeuilRuptureArticle())
                .quantieStocksArticle(article.getQuantieStocksArticle())
                .categorieUuid(article.getCategorie().getUuid())
                .piece(article.getPiece())
                .build();
    }
}
