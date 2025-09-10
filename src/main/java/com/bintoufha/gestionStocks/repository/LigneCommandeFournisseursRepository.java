package com.bintoufha.gestionStocks.repository;


import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LigneCommandeFournisseursRepository extends JpaRepository<LigneCommandeFournisseurs,UUID> {
    Optional<LigneCommandeFournisseurs> findByUuid(UUID uuidLigneCommande);


    List<LigneCommandeFournisseurs> findAllByArticles_Uuid(UUID uuidCommande);

    void deleteByUuid(UUID uuidLigneCommande);
}
