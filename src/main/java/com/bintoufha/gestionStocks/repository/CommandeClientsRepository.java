package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.CommandeClients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommandeClientsRepository extends JpaRepository<CommandeClients,UUID > {


    Optional<CommandeClients> findByUuid(UUID uuid);


    Optional<CommandeClients> findByRefernce(String refernce);

    void deleteByUuid(UUID uuid);
}
