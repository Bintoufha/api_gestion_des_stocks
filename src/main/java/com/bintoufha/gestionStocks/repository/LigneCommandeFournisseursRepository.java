package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LigneCommandeFournisseursRepository extends JpaRepository<LigneCommandeFournisseurs,UUID> {
}
