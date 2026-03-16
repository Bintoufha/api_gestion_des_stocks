package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs,UUID> {
    Optional<Utilisateurs> findUtilisateursByEmailIgnoreCase(String email);

    Optional<Utilisateurs> findByUuid(UUID uuid);

    int countByEntreprise_Uuid(UUID uuidEntreprise);

    Optional<Utilisateurs> findByNomPrenomUtilisateurs(String username);

    boolean existsByRoles(Roles role);

    boolean existsByUsername(String superadmin);

    List<Utilisateurs> findByEntrepriseUuid(UUID entrepriseUuid);

    @Query("SELECT u FROM utilisateurs u JOIN u.roles r WHERE r.name = :roleName")
    List<Utilisateurs> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM utilisateurs u WHERE u.entreprise.uuid = :entrepriseUuid AND u.actif = true")
    List<Utilisateurs> findActiveByEntrepriseUuid(@Param("entrepriseUuid") UUID entrepriseUuid);

    @Query("SELECT u FROM utilisateurs u WHERE LOWER(u.nomComplet) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Utilisateurs> searchByNomComplet(@Param("search") String search);

}
