package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Categories;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ArticlesDto {
    private UUID uuid;

    private String nomArticle;

    private BigDecimal prixUnitaireArticle;

    private BigDecimal prixEngrosArticle;

    private BigDecimal prixDetailleArticle;

    private BigDecimal seuilRuptureArticle;

    private BigDecimal quantieStocksArticle;

    private String photoArticle;

    private UUID idEntreprise;

    private Categories categorie;

    public static ArticlesDto fromEntity(Articles articles){
        if (articles == null){
            return  null;
        }
        return ArticlesDto.builder()
                .nomArticle(articles.getNomArticle())
                .prixDetailleArticle(articles.getPrixDetailleArticle())
                .prixEngrosArticle(articles.getPrixEngrosArticle())
                .prixUnitaireArticle(articles.getPrixUnitaireArticle())
                .quantieStocksArticle(articles.getQuantieStocksArticle())
                .categorie(articles.getCategorie())
                .photoArticle(articles.getPhotoArticle())
                .idEntreprise(articles.getIdEntreprise())
                .build();
    }
    public static Articles toEntity(ArticlesDto articlesDto){
        if (articlesDto == null){
            return  null;
        }
        Articles articles = new Articles();
        articles.setNomArticle(articlesDto.getNomArticle());
        articles.setPrixUnitaireArticle(articlesDto.getPrixUnitaireArticle());
        articles.setPrixEngrosArticle(articlesDto.getPrixEngrosArticle());
        articles.setPrixDetailleArticle(articlesDto.getPrixDetailleArticle());
        articles.setQuantieStocksArticle(articlesDto.getQuantieStocksArticle());
        articles.setCategorie(articlesDto.getCategorie());
        articles.setIdEntreprise(articles.getIdEntreprise());
        articles.setPhotoArticle(articlesDto.getPhotoArticle());
        return articles;
    }
}
