package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.CommandeFournisseursApi;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.commandeFournisseurs.CommandeFournisseursListDto;
import com.bintoufha.gestionStocks.model.EtatCommande;
import com.bintoufha.gestionStocks.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class CommandeFournisseurControllers implements CommandeFournisseursApi {

    private CommandeFournisseurService commandeFournisseurService;

    @Autowired
    public CommandeFournisseurControllers(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    @Override
    public ResponseEntity<CommandeFournisseurSaveDto> save(CommandeFournisseurSaveDto commandeFournisseursDto) {
        return ResponseEntity.ok(commandeFournisseurService.save(commandeFournisseursDto));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> findByUUID(UUID uuidCmdFournisseur) {
        return ResponseEntity.ok(commandeFournisseurService.findByUUID(uuidCmdFournisseur));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> findByReference(String reference) {
        return ResponseEntity.ok(commandeFournisseurService.findByReference(reference));
    }

    @Override
    public ResponseEntity<List<CommandeFournisseursListDto>> findAll() {
        return ResponseEntity.ok(commandeFournisseurService.findAll());
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> delete(UUID uuid) {
        commandeFournisseurService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> UpdteEtatCommande(UUID uuidCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeFournisseurService.UpdteEtatCommande(uuidCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> UpdateQuantiteCommande(UUID uuidCommande, UUID uuidLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeFournisseurService.UpdateQuantiteCommande(uuidCommande,uuidLigneCommande,quantite));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> UpdateFournisseurs(UUID uuidCommande, UUID uuidfournisseur) {
        return ResponseEntity.ok(commandeFournisseurService.UpdateFournisseurs(uuidCommande,uuidfournisseur));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> UpdateArticle(UUID uuidCommande, UUID uuidLigneCommande, UUID newdUuidArticle) {
        return ResponseEntity.ok(commandeFournisseurService.UpdateArticle(uuidCommande,uuidLigneCommande,newdUuidArticle));
    }

    @Override
    public ResponseEntity<CommandeFournisseursListDto> DeleteArticle(UUID uuidCommande, UUID uuidLigneCommande) {
        return ResponseEntity.ok(commandeFournisseurService.DeleteArticle(uuidCommande,uuidLigneCommande));
    }

//    @Override
//    public List<CommandeFournisseursListDto> findAllLigneCommandeFournisseurByUuid(UUID uuidCommande) {
//        return commandeFournisseurService.findAllLigneCommandeFournisseurByUuid(uuidCommande);
//    }
}
