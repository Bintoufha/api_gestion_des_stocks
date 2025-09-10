package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LigneCommandeClientsRepository extends JpaRepository<LigneCommandeClients,UUID> {
    List<LigneCommandeClients> findAllByUuid(UUID uuidCommandeClienst);

    Optional<LigneCommandeClients> findByUuid(UUID uuidLigneCommande);
    Optional<LigneCommandeClients> deleteByUuid(UUID uuidLigneCommande);

  //  List<LigneCommandeClients> findAllByArticles_Uuid(UUID uuidCommandeClient);
}
