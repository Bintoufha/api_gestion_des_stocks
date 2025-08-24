package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UtilisateursRepository extends JpaRepository<Utilisateurs,UUID> {
}
