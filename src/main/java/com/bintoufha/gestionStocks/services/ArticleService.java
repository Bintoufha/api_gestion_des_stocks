package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneVenteDto;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticlesDto save (ArticlesDto dto);

    ArticlesDto findByUuid(UUID uuid);

    //ArticlesDto findByCodeArticle (String codeArticle);

    List<ArticlesDto> findAll();

    List<LigneVenteDto> findHistoriqueVente(UUID uuidArticle);

    List<LigneCommandeClientsDto> findHistoriqueCommandeCLients(UUID uuidArticle);

    List<LigneCommandeFournisseursDto> findHistoriqueCommandeFournisseurs(UUID uuidArticle);

    List<ArticlesDto> findAllArticleByCategorieUuid (UUID uuidCategorie);

    void delete(UUID uuid);
}
