package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.VentesDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.services.VenteService;
import com.bintoufha.gestionStocks.validator.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class  VenteServiceImpl implements VenteService {

    @Override
    public VentesDto save(VentesDto ventesDto) {
        List<String> errors = VenteValidator.validate(ventesDto);
        if (!errors.isEmpty()){
            log.warn("vente n'est pas valide");
            throw new InvalEntityException("l'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        List<String> articleErrors = new ArrayList<>();
        ventesDto.
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
