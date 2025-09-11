package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.dto.LigneVenteDto;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.LigneVente;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface LigneVenteRepository extends JpaRepository<LigneVente,UUID> {

    List<LigneVente> findAllByUuid(UUID uuidArticle);

    List<LigneVente> findAllByVente_Uuid(UUID uuidVente);
}
