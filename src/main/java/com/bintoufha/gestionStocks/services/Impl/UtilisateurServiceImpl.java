package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.CategoriesDto;
import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.concurrent.UncheckedFuture.map;


@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final DefaultErrorAttributes errorAttributes;
    private UtilisateursRepository utilisateursRepository;

    @Autowired
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

   @Override
    public UtilisateursDto findByEmail(String email) {
        return utilisateursRepository.findUtilisateursByEmailIgnoreCase(email)
                .map(UtilisateursDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'email fourni",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

}
