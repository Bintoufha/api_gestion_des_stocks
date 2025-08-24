package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Articles,UUID> {


    //Optional<Articles> findByCodeArticles(String codeArticle);


    Optional<Articles> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
