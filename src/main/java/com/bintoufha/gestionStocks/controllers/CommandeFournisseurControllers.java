package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.CommandeFournisseursApi;
import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;
import com.bintoufha.gestionStocks.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CommandeFournisseursDto> save(CommandeFournisseursDto commandeFournisseursDto) {
        return ResponseEntity.ok(commandeFournisseurService.save(commandeFournisseursDto));
    }

    @Override
    public ResponseEntity<CommandeFournisseursDto> findByUUID(UUID uuidCmdFournisseur) {
        return ResponseEntity.ok(commandeFournisseurService.findByUUID(uuidCmdFournisseur));
    }

    @Override
    public ResponseEntity<CommandeFournisseursDto> findByReference(String reference) {
        return ResponseEntity.ok(commandeFournisseurService.findByReference(reference));
    }

    @Override
    public ResponseEntity<List<CommandeFournisseursDto>> findAll() {
        return ResponseEntity.ok(commandeFournisseurService.findAll());
    }

    @Override
    public ResponseEntity<CommandeFournisseursDto> delete(UUID uuid) {
        commandeFournisseurService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
