package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.ArticlesApi;
import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneVenteDto;
import com.bintoufha.gestionStocks.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ArticlesControllers implements ArticlesApi {

    private final ArticleService articleService;

   @Autowired
    public ArticlesControllers(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public ArticlesDto save(ArticlesDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticlesDto findByUUID(UUID uuid) {
        return articleService.findByUuid(uuid);
    }

    @Override
    public List<ArticlesDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(UUID uuidArticle) {
        return articleService.findHistoriqueVente(uuidArticle);
    }

    @Override
    public List<LigneCommandeClientsDto> findHistoriqueCommandeCLients(UUID uuidArticle) {
        return articleService.findHistoriqueCommandeCLients(uuidArticle);
    }

    @Override
    public List<LigneCommandeFournisseursDto> findHistoriqueCommandeFournisseurs(UUID uuidArticle) {
        return articleService.findHistoriqueCommandeFournisseurs(uuidArticle);
    }

    @Override
    public List<ArticlesDto> findAllArticleByCategorieUuid(UUID uuidCategorie) {
        return articleService.findAllArticleByCategorieUuid(uuidCategorie);
    }

    //    @Override
//    public ArticlesDto findByCodeArticle(String codeArticle) {
//        return articleService.findByCodeArticle(codeArticle);
//    }

    @Override
    public void delete(UUID uuid) {
        articleService.delete(uuid);
    }
}
