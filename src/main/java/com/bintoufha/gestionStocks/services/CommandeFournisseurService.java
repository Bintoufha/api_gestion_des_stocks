package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.CommandeClientsDto;
import com.bintoufha.gestionStocks.dto.CommandeFournisseursDto;

import java.util.List;
import java.util.UUID;

public interface CommandeFournisseurService {

    CommandeFournisseursDto save (CommandeFournisseursDto commandeFournisseursDto);

    CommandeFournisseursDto findByUUID (UUID uuid);

    CommandeFournisseursDto findByReference (String reference);

    List<CommandeFournisseursDto> findAll ();

    void delete(UUID uuid);
}
