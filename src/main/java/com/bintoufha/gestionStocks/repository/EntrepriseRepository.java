package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.EntrepriseConfig;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.StatutEntreprise;
import com.bintoufha.gestionStocks.model.TypeEntreprises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntrepriseRepository extends JpaRepository<Entreprises,UUID> {
    Optional<Entreprises> findByUuid(UUID uuid);

    List<Entreprises> findByTypeBoutique(TypeEntreprises typeBoutique);
    List<Entreprises> findByVille(String ville);
    List<Entreprises> findByStatut(StatutEntreprise statut);
    Optional<Entreprises> findByNomEntreprise(String nom);
    boolean existsByNomEntreprise(String nom);

    @Query("SELECT b FROM Entreprise b WHERE b.typeEntreprise.uuid = :typeId")
    List<Entreprises> findByTypeEntrepriseUuid(@Param("typeId") UUID typeId);

    @Query("SELECT b FROM Entreprise b WHERE LOWER(b.nomEntreprise) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Entreprises> searchByNomEntreprise(@Param("search") String search);

    @Transactional
    void deleteByUuid(UUID uuid);

    List<Entreprises> findByTypeEntreprises(TypeEntreprises typeEntreprises);
}
