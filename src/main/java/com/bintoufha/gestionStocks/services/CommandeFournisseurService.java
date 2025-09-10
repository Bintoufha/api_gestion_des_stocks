package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeFournisseursDto;
import com.bintoufha.gestionStocks.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CommandeFournisseurService {

    CommandeFournisseursDto save (CommandeFournisseursDto commandeFournisseursDto);

    CommandeFournisseursDto UpdteEtatCommande(UUID uuidCommande, EtatCommande etatCommande);

    CommandeFournisseursDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite);

    CommandeFournisseursDto UpdateFournisseurs (UUID uuidCommande, UUID uuidfournisseur);

    CommandeFournisseursDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande,UUID newdUuidArticle);

    CommandeFournisseursDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande);

    CommandeFournisseursDto findByUUID (UUID uuid);

    CommandeFournisseursDto findByReference (String reference);

    List<CommandeFournisseursDto> findAll ();
    List<LigneCommandeFournisseursDto> findAllLigneCommandeFournisseurByUuid(UUID uuidCommande);

    void delete(UUID uuid);
}
