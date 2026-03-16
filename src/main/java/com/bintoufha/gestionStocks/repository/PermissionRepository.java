package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission,UUID> {

    Optional<Permission> findByCode(String code);

    Optional<Permission> findByUuid(UUID uuid);

    boolean existsByCode(String code);

    List<Permission> findByModule(String module);

    List<Permission> findByCategory(String category);

    @Query("SELECT p FROM Permission p WHERE p.module = :module AND p.category = :category")
    List<Permission> findByModuleAndCategory(
            @Param("module") String module,
            @Param("category") String category
    );

    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.name = :roleName")
    List<Permission> findByRoleName(@Param("roleName") String roleName);
}
