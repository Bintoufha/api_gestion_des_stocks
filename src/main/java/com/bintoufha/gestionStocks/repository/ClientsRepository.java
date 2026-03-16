package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ClientsRepository extends JpaRepository<Clients,UUID> {

    Optional<Clients> findByUuid(UUID uuid);
}
