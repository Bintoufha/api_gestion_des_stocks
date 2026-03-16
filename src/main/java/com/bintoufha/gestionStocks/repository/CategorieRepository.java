package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Categories,UUID> {
    
    Optional<Categories> findByUuid(UUID uuid);

    Optional<Categories> findByCode(String code);

    List<Categories> findByCategorieParentIsNull();
    List<Categories> findByCategorieParentId(Long parentId);
    boolean existsByNom(String nom);
    boolean existsByCode(String code);

    @Query("SELECT c FROM Categorie c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Categories> searchByNom(@Param("search") String search);


    void deleteByUuid(UUID uuid);
}
