package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommandeFournisseursRepository extends JpaRepository<CommandeFournisseurs,UUID> {
}
