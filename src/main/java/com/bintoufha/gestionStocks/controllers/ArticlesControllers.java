package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.ArticlesApi;
import com.bintoufha.gestionStocks.dto.article.ArticleListDto;
import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs.LigneCommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;
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
    public ArticleSaveDto save(ArticleSaveDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleListDto findByUUID(UUID uuid) {
        return articleService.findByUuid(uuid);
    }

    @Override
    public List<ArticleListDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(UUID uuidArticle) {
        return articleService.findHistoriqueVente(uuidArticle);
    }

    @Override
    public List<LigneCommandeClientSaveDto> findHistoriqueCommandeCLients(UUID uuidArticle) {
        return articleService.findHistoriqueCommandeCLients(uuidArticle);
    }

    @Override
    public List<LigneCommandeFournisseurSaveDto> findHistoriqueCommandeFournisseurs(UUID uuidArticle) {
        return articleService.findHistoriqueCommandeFournisseurs(uuidArticle);
    }

//    @Override
//    public List<ArticlesDto> findAllArticleByCategorieUuid(UUID uuidCategorie) {
//        return articleService.findAllArticleByCategorieUuid(uuidCategorie);
//    }

    //    @Override
//    public ArticlesDto findByCodeArticle(String codeArticle) {
//        return articleService.findByCodeArticle(codeArticle);
//    }

    @Override
    public void delete(UUID uuid) {
        articleService.delete(uuid);
    }
}
