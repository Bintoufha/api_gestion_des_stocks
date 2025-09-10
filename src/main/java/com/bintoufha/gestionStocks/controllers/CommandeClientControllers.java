package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.CommandeClientsApi;
import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.services.CommandeClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CommandeClientsDto> save(CommandeClientsDto commandeDto) {
        return ResponseEntity.ok(commandeClientsService.save(commandeDto));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> UpdteCommandeClients(UUID uuidCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeClientsService.UpdteCommandeClients(uuidCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeClientsService.UpdateQuantiteCommande(uuidCommande, uuidLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> UpdateClient(UUID uuidCommande, UUID uuidClient) {
        return ResponseEntity.ok(commandeClientsService.UpdateClient(uuidCommande, uuidClient));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        return ResponseEntity.ok(commandeClientsService.UpdateArticle(uuidCommande, uuidLigneCommande, newdUuidArticle));
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientsDto>> findAllLignesCommandeClientsByUuid(UUID uuidCommandeClients) {
        return ResponseEntity.ok(commandeClientsService.findAllLignesCommandeClientsByUuid(uuidCommandeClients));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {
        return ResponseEntity.ok(commandeClientsService.DeleteArticle(uuidCommande,uuidLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> findByUUID(UUID uuid) {
        return ResponseEntity.ok(commandeClientsService.findByUUID(uuid));
    }

    @Override
    public ResponseEntity<CommandeClientsDto> findByReference(String reference) {
        return ResponseEntity.ok(commandeClientsService.findByReference(reference));
    }

    @Override
    public ResponseEntity<List<CommandeClientsDto>> findAll() {
        return ResponseEntity.ok(commandeClientsService.findAll());
    }

    @Override
    public ResponseEntity<CommandeClientsDto> delete(UUID uuid) {
        commandeClientsService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
