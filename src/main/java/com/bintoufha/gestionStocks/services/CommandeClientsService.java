package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;

import java.util.List;
import java.util.UUID;

public interface CommandeClientsService {

    CommandeClientsDto save (CommandeClientsDto commandeClientsDto);

    CommandeClientsDto findByUUID (UUID uuid);

    CommandeClientsDto findByReference (String reference);

    List<CommandeClientsDto> findAll ();

    void delete(UUID uuid);
}
