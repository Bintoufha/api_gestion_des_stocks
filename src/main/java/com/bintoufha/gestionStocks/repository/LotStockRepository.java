package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LotStocks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LotStockRepository extends JpaRepository<LotStocks, UUID> {

//    List<LotStocks> findByArticlesAndIdEntrepriseOrderBycreationDateAsc(Articles uuidArticle, UUID uuidEntreprise);
//    List<LotStocks> findByArticlesAndIdEntrepriseOrderBycreationDateDesc(Articles uuidArticle, UUID uuidEntreprise);

    List<LotStocks> findByIdEntrepriseAndArticles(UUID uuidEntreprise, Articles uuidArticle);
}
