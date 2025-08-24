package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VenteRepositry extends JpaRepository<Ventes,UUID> {
}
