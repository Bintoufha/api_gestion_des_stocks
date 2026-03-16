package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.article.ArticleListDto;
import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs.LigneCommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleSaveDto save (ArticleSaveDto dto);

    ArticleListDto findByUuid(UUID uuid);

    //ArticlesDto findByCodeArticle (String codeArticle);

    List<ArticleListDto> findAll();

    List<LigneVenteDto> findHistoriqueVente(UUID uuidArticle);

    List<LigneCommandeClientSaveDto> findHistoriqueCommandeCLients(UUID uuidArticle);

    List<LigneCommandeFournisseurSaveDto> findHistoriqueCommandeFournisseurs(UUID uuidArticle);

//    List<ArticlesDto> findAllArticleByCategorieUuid (UUID uuidCategorie);

    void delete(UUID uuid);
}
