package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Entreprises;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntrepriseRepository extends JpaRepository<Entreprises,UUID> {
    Optional<Entreprises> findByUuid(UUID uuid);
}
