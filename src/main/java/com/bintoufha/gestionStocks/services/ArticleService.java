package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.ArticlesDto;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticlesDto save (ArticlesDto dto);

    ArticlesDto findByUuid(UUID uuid);

    //ArticlesDto findByCodeArticle (String codeArticle);

    List<ArticlesDto> findAll();

    void delete(UUID uuid);
}
