package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.model.EntrepriseConfig;
import com.bintoufha.gestionStocks.model.Entreprises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface EntrepriseConfigRepository extends JpaRepository<EntrepriseConfig, UUID> {
    Optional<EntrepriseConfig> findByUuid(UUID uuid);

    Optional<EntrepriseConfig> findByActive(Boolean active);

    Optional<EntrepriseConfig> findByIdEntreprise(UUID idEntreprise);


    List<EntrepriseConfig> findAllByIdEntreprise(UUID idEntreprise);


    Optional<EntrepriseConfig> findByEntrepriseAndCategorie(Entreprises entreprises, Categories categorie);

    List<EntrepriseConfig> findByEntreprises(Entreprises entreprises);

    List<EntrepriseConfig> findByCategorie(Categories categorie);

    List<EntrepriseConfig> findByEntrepriseAndActiveTrue(Entreprises entreprises);

    @Query("SELECT t FROM EntrepriseConfig t WHERE t.Entreprise.uuid = :EntrepriseUuid AND t.categorie.uuid = :CategorieUuid")
    Optional<EntrepriseConfig> findByEntrepriseUuidAndCategorieUuid(
            @Param("EntrepriseUuid") UUID EntrepriseUuid,
            @Param("CategorieUuid") UUID CategorieUuid
    );

    @Query("SELECT t FROM EntrepriseConfig t WHERE t.entreprise.uuid = :EntrepriseUuid")
    List<EntrepriseConfig> findByBoutiqueId(@Param("EntrepriseUuid") UUID EntrepriseUuid);

    @Query("SELECT t FROM EntrepriseConfig t WHERE t.categorie.id = :CategorieUuid")
    List<EntrepriseConfig> findByCategorieUuid(@Param("UUID CategorieUuid") UUID CategorieUuid);

    @Query("SELECT COUNT(t) FROM EntrepriseConfig t WHERE t.boutique.id = :boutiqueId")
    Long countByEntrepriseUuid(@Param("boutiqueId") Long boutiqueId);

    @Query("SELECT COUNT(t) FROM EntrepriseConfig t WHERE t.categorie.uuid = :CategorieUuid")
    Long countByCategorieUuid(@Param("CategorieUuid") UUID CategorieUuidCategorieUuid);

    boolean existsByEntrepriseAndCategorie(Entreprises entreprises, Categories categorie);

}
