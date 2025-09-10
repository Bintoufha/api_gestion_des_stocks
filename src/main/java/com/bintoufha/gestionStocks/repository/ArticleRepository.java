package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Articles, UUID> {
    //Optional<Articles> findByCodeArticles(String codeArticle);

    Optional<Articles> findByUuid(UUID uuid);

    List<Articles> findAllByCategorieUuid(UUID uuidCategorie);

    void deleteByUuid(UUID uuid);
}
