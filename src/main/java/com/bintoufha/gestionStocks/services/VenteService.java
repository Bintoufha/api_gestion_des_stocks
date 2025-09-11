package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.VentesDto;

import java.util.List;
import java.util.UUID;

public interface VenteService {

    VentesDto save(VentesDto ventesDto);

    VentesDto findByUuid (UUID uuid);

    VentesDto findByReference(String reference);

    List<VentesDto> findAll();

    void deleteByUuid(UUID uuid);
}
