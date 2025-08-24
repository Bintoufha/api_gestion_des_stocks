package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.FournisseursDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



public interface FournisseurService {
    FournisseursDto save (FournisseursDto fournisseursDto);

    FournisseursDto findByUuid(UUID uuid);

    List<FournisseursDto> findAll();

    void deleteByUuid(UUID uuid);
}
