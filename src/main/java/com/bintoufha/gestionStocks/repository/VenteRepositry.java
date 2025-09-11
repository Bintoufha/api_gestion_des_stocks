package com.bintoufha.gestionStocks.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.bintoufha.gestionStocks.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VenteRepositry extends JpaRepository<Ventes,UUID> {

    Optional<Ventes> findVentesByReference(String code);

    Optional<Ventes> findByUuid(UUID uuid);

    Optional<Ventes> deleteByUuid(UUID uuid);
}
