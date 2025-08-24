package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.MouvementStocks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MouvementStockRepository extends JpaRepository<MouvementStocks,UUID> {
}
