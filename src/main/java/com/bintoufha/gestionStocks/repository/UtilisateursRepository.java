package com.bintoufha.gestionStocks.repository;

import com.bintoufha.gestionStocks.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtilisateursRepository extends JpaRepository<Utilisateurs,UUID> {
    Optional<Utilisateurs> findUtilisateursByEmailIgnoreCase(String email);
//    d2742360-8270-11f0-b6cb-9d9f53b1868b
//    d2742361-8270-11f0-b6cb-9d9f53b1868b
//    d2742362-8270-11f0-b6cb-9d9f53b1868b
//    d2742363-8270-11f0-b6cb-9d9f53b1868b
//    d2742364-8270-11f0-b6cb-9d9f53b1868b
//    d2742365-8270-11f0-b6cb-9d9f53b1868b
//    d2742366-8270-11f0-b6cb-9d9f53b1868b
//    d2742367-8270-11f0-b6cb-9d9f53b1868b
//    d2742368-8270-11f0-b6cb-9d9f53b1868b
//    d2742369-8270-11f0-b6cb-9d9f53b1868b

}
