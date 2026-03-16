package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.Roles;
import com.bintoufha.gestionStocks.model.UserRoleEntreprise;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleBoutiqueRepository extends JpaRepository<UserRoleEntreprise, UUID> {

    List<UserRoleEntreprise> findByUtilisateurs(Utilisateurs user);

    List<UserRoleEntreprise> findByUtilisateursAndActiveTrue(Utilisateurs user);

    Optional<UserRoleEntreprise> findByUtilisateursAndRoleAndEntreprise(Utilisateurs user, Roles role, Entreprises entreprises);

    @Query("SELECT urb FROM UserRoleEntreprise urb WHERE urb.utilisateurs.uuid = :userId AND urb.active = true")
    List<UserRoleEntreprise> findActiveByUtilisateursUuid(@Param("userId") UUID userId);

    @Query("SELECT urb FROM UserRoleEntreprise urb WHERE urb.utilisateurs.uuid = :userId AND urb.role.id = :roleId AND urb.active = true")
    List<UserRoleEntreprise> findActiveByUserAndRole(@Param("userId") UUID userId, @Param("roleId") UUID roleId);

    @Query("SELECT urb FROM UserRoleEntreprise urb WHERE urb.boutique.id = :boutiqueId AND urb.active = true")
    List<UserRoleEntreprise> findActiveByBoutiqueId(@Param("boutiqueId") UUID boutiqueId);

    @Query("SELECT DISTINCT urb.user FROM UserRoleEntreprise urb WHERE urb.boutique.id = :boutiqueId AND urb.active = true")
    List<Utilisateurs> findUsersByBoutiqueId(@Param("boutiqueId") UUID boutiqueId);

    @Query("SELECT urb FROM UserRoleEntreprise urb WHERE urb.utilisateurs.uuid = :userId AND urb.boutique.id = :boutiqueId AND urb.active = true")
    List<UserRoleEntreprise> findActiveByUserAndBoutique(@Param("userId") UUID userId, @Param("boutiqueId") Long boutiqueId);

    @Query("SELECT COUNT(urb) > 0 FROM UserRoleEntreprise urb WHERE urb.utilisateurs.uuid = :userId AND urb.role.name = :roleName AND urb.active = true")
    boolean userHasRole(@Param("userId") UUID userId, @Param("roleName") String roleName);

    @Query("SELECT COUNT(urb) > 0 FROM UserRoleEntreprise urb WHERE urb.utilisateurs.uuid = :userId AND urb.role.name = :roleName AND urb.boutique.id = :boutiqueId AND urb.active = true")
    boolean userHasRoleForBoutique(@Param("userId") UUID userId, @Param("roleName") String roleName, @Param("boutiqueId") UUID boutiqueId);
}
