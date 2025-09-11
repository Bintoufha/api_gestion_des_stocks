package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.MouvementStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MouvementStockRepository extends JpaRepository<MouvementStocks,UUID> {

    @Query("select sum(m.quantite) from mvtStocks m where m.articles.uuid = :uuidArticle ")
    BigDecimal stockReelArticle(@Param("uuidArticle") UUID uuidArticle);

    List<MouvementStocks> findAllByArticle_Uuid(UUID uuidArticle);
}
