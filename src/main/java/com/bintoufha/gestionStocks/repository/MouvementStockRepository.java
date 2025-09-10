package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.MouvementStocks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MouvementStockRepository extends JpaRepository<MouvementStocks,UUID> {

    BigDecimal stockReelArticle(UUID uuidArticle);

    List<MouvementStocks> findAllByArticle_Uuid(UUID uuidArticle);
}
