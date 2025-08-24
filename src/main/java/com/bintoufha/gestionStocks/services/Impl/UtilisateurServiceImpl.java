package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final DefaultErrorAttributes errorAttributes;
    private UtilisateursRepository utilisateursRepository;

    public UtilisateurServiceImpl(
            DefaultErrorAttributes errorAttributes,
            UtilisateursRepository utilisateursRepository
    ) {
        this.errorAttributes = errorAttributes;
        this.utilisateursRepository = utilisateursRepository;
    }

    @Override
    public UtilisateursDto save(UtilisateursDto utilisateursDto) {
        return null;
    }

    @Override
    public UtilisateursDto findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public List<UtilisateursDto> findAll() {
        return List.of();
    }

    @Override
    public void deleteByUuid(UUID uuid) {

    }
}
