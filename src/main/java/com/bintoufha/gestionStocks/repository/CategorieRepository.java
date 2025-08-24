package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Categories,UUID> {
    Optional<Categories> findByUuid(UUID uuid);

    Optional<Categories> findByCode(String code);
    
    void deleteByUuid(UUID uuid);
}
