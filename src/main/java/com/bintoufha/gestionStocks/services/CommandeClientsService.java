package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CommandeClientsService {

    CommandeClientsDto save (CommandeClientsDto commandeClientsDto);

    CommandeClientsDto findByUUID (UUID uuid);

    CommandeClientsDto UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande);

    CommandeClientsDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite);

    CommandeClientsDto UpdateClient (UUID uuidCommande, UUID uuidClient);

    CommandeClientsDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande,UUID newdUuidArticle);

    CommandeClientsDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande);

    CommandeClientsDto findByReference (String reference);

    List<CommandeClientsDto> findAll ();

    List<LigneCommandeClientsDto> findAllLignesCommandeClientsByUuid(UUID uuidCommandeClients);

    void delete(UUID uuid);
}
