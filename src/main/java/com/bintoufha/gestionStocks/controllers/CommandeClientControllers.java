package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.CommandeClientsApi;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientListDto;
import com.bintoufha.gestionStocks.dto.commandeClient.CommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class CommandeClientControllers implements CommandeClientsApi {

    private CommandeClientsService commandeClientsService;

    @Autowired
    public CommandeClientControllers(CommandeClientsService commandeClientsService) {
        this.commandeClientsService = commandeClientsService;
    }

    @Override
    public ResponseEntity<CommandeClientSaveDto> save(CommandeClientSaveDto commandeDto) {
        return ResponseEntity.ok(commandeClientsService.save(commandeDto));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeClientsService.UpdteCommandeClients(uuidCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeClientsService.UpdateQuantiteCommande(uuidCommande, uuidLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> UpdateClient(UUID uuidCommande, UUID uuidClient) {
        return ResponseEntity.ok(commandeClientsService.UpdateClient(uuidCommande, uuidClient));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        return ResponseEntity.ok(commandeClientsService.UpdateArticle(uuidCommande, uuidLigneCommande, newdUuidArticle));
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientSaveDto>> findAllLignesCommandeClientsByUuid(UUID uuidCommandeClients) {
        return ResponseEntity.ok(commandeClientsService.findAllLignesCommandeClientsByUuid(uuidCommandeClients));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {
        return ResponseEntity.ok(commandeClientsService.DeleteArticle(uuidCommande,uuidLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> findByUUID(UUID uuid) {
        return ResponseEntity.ok(commandeClientsService.findByUUID(uuid));
    }

    @Override
    public ResponseEntity<CommandeClientListDto> findByReference(String reference) {
        return ResponseEntity.ok(commandeClientsService.findByReference(reference));
    }

    @Override
    public ResponseEntity<List<CommandeClientListDto>> findAll() {
        return ResponseEntity.ok(commandeClientsService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        commandeClientsService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
