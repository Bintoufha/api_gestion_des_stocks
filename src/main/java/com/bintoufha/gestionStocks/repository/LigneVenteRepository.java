package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.dto.LigneVenteDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LigneVenteRepository extends JpaRepository<LigneVente,UUID> {

    List<LigneVente> findAllByUuid(UUID uuidArticle);
}
