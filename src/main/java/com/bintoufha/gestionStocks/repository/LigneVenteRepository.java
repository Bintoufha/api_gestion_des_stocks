package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LigneVenteRepository extends JpaRepository<LigneVente,UUID> {
}
