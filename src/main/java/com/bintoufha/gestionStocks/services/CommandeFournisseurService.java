package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CommandeFournisseurService {

    CommandeFournisseurSaveDto save (CommandeFournisseurSaveDto commandeFournisseursDto);

    CommandeFournisseursListDto UpdteEtatCommande(UUID uuidCommande, EtatCommande etatCommande);

    CommandeFournisseursListDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite);

    CommandeFournisseursListDto UpdateFournisseurs (UUID uuidCommande, UUID uuidfournisseur);

    CommandeFournisseursListDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande,UUID newdUuidArticle);

    CommandeFournisseursListDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande);

    CommandeFournisseursListDto findByUUID (UUID uuid);

    CommandeFournisseursListDto findByReference (String reference);

    List<CommandeFournisseursListDto> findAll ();

//    List<LigneCommandeClientSaveDto> findAllLigneCommandeFournisseurByUuid(UUID uuidCommande);

    void delete(UUID uuid);
}
