package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.VentesDto;
import com.bintoufha.gestionStocks.services.VenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {

    @Override
    public VentesDto save(VentesDto ventesDto) {
        return null;
    }

    @Override
    public VentesDto findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public VentesDto findByReference(String reference) {
        return null;
    }

    @Override
    public VentesDto findAll() {
        return null;
    }

    @Override
    public VentesDto deleteByUuid(UUID uuid) {
        return null;
    }
}
