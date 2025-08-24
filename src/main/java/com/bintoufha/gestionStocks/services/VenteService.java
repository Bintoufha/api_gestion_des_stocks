package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.VentesDto;

import java.util.UUID;

public interface VenteService {

    VentesDto save(VentesDto ventesDto);

    VentesDto findByUuid (UUID uuid);

    VentesDto findByReference(String reference);

    VentesDto findAll();

    VentesDto deleteByUuid(UUID uuid);
}
