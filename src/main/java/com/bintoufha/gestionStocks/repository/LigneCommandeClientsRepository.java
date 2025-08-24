package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.LigneCommandeClients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LigneCommandeClientsRepository extends JpaRepository<LigneCommandeClients,UUID> {
}
