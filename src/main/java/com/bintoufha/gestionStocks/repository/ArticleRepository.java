package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;


public interface ArticleRepository extends JpaRepository<Articles, UUID> {
    //Optional<Articles> findByCodeArticles(String codeArticle);

    Optional<Articles> findByUuid(UUID uuid);

    List<Articles> findAllByCategorieUuid(UUID uuidCategorie);

    List<Articles> findByCreationDateBetween(Date start, Date end);

    void deleteByUuid(UUID uuid);
}
