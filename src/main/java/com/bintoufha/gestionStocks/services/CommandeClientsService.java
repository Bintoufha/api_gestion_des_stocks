package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientListDto;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CommandeClientsService {

    CommandeClientSaveDto save (CommandeClientSaveDto commandeClientsDto);

    CommandeClientListDto findByUUID (UUID uuid);

    CommandeClientListDto UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande);

    CommandeClientListDto UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite);

    CommandeClientListDto UpdateClient (UUID uuidCommande, UUID uuidClient);

    CommandeClientListDto UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande,UUID newdUuidArticle);

    CommandeClientListDto DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande);

    CommandeClientListDto findByReference (String reference);

    List<CommandeClientListDto> findAll ();

    List<LigneCommandeClientSaveDto> findAllLignesCommandeClientsByUuid(UUID uuidCommandeClients);

    void delete(UUID uuid);
}
