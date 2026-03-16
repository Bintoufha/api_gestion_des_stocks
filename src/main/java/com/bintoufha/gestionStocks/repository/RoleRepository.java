package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.RoleScope;
import com.bintoufha.gestionStocks.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles,UUID> {

    Optional<Roles> findByName(String name);

    Optional<Roles> findByUuid(UUID uuid);

    boolean existsByName(String name);

    List<Roles> findByScope(RoleScope scope);


    // CORRECTION : Utiliser "Roles" au lieu de "Role"
    @Query("SELECT r FROM Roles r WHERE r.parentRole.id = :parentId")
    List<Roles> findByParentUuid(@Param("parentId") UUID parentUuid);

    @Query("SELECT r FROM Roles r WHERE r.scope = 'GLOBAL'")
    List<Roles> findGlobalRoles();

    @Query("SELECT r FROM Roles r WHERE r.scope = 'BOUTIQUE'")
    List<Roles> findEntrepriseRoles();

}
