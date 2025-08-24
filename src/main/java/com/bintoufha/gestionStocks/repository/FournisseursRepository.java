package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FournisseursRepository extends JpaRepository<Fournisseurs,UUID> {
    Optional<Fournisseurs> findByUuid(UUID uuid);
}
