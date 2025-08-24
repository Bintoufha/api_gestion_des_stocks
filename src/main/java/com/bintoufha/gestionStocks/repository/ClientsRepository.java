package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientsRepository extends JpaRepository<Clients,UUID> {

    //Optional<Clients> findUUIDBy(UUID uuid);

    Optional<Clients> findByUuid(UUID uuid);

    //void deleteByUuid(UUID );
}
