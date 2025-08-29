package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;

import java.util.List;
import java.util.UUID;

public interface UtilisateurService {
    UtilisateursDto save (UtilisateursDto utilisateursDto);

    UtilisateursDto findByUuid(UUID uuid);

    UtilisateursDto findByEmail(String email);

    List<UtilisateursDto> findAll();

    void deleteByUuid(UUID uuid);
}
