package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface EntrepriseService {
    EntrepriseDto save (EntrepriseDto entrepriseDto);

    EntrepriseDto findByUuid(UUID uuid);

    List<EntrepriseDto> findAll();

    void deleteByUuid(UUID uuid);
}
