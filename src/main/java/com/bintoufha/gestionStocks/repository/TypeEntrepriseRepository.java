package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.TypeEntreprises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TypeEntrepriseRepository extends JpaRepository<TypeEntreprises, UUID> {

    Optional<TypeEntreprises> findByNomTypeEntreprise(String nom);
    Optional<TypeEntreprises> findByCode(String code);
    Optional<TypeEntreprises> findByUuid(UUID uuid);
    boolean existsByNomTypeEntreprise(String nom);
    boolean existsByCode(String code);
}
