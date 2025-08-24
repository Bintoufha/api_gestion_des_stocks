package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.FournisseursDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import com.bintoufha.gestionStocks.repository.FournisseursRepository;
import com.bintoufha.gestionStocks.services.FournisseurService;
import com.bintoufha.gestionStocks.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl  implements FournisseurService {

    private final DefaultErrorAttributes errorAttributes;
    private FournisseursRepository fournisseursRepository;

    public FournisseurServiceImpl(DefaultErrorAttributes errorAttributes, FournisseursRepository fournisseursRepository) {
        this.fournisseursRepository = fournisseursRepository;
        this.errorAttributes = errorAttributes;
    }

    @Override
    public FournisseursDto save(FournisseursDto fournisseursDto) {
        List<String> errors = FournisseurValidator.validate(fournisseursDto);
        if (!errors.isEmpty()) {
            log.error("donnee non valide");
            throw new InvalEntityException("tout les information du fournisseur n'ont pas ete renseigner",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND, errors);
        }
        return FournisseursDto.fromEntity(
                fournisseursRepository.save(FournisseursDto.toEntity(fournisseursDto))
        );
    }

    @Override
    public FournisseursDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant client est introuvable");
        }

        Optional<Fournisseurs> fournisseurs = fournisseursRepository.findByUuid(uuid);

        return fournisseurs
                .map(FournisseursDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "le fournisseurs avec identifiant : " + uuid + "n'existe pas dans la base de donnee",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<FournisseursDto> findAll() {
        return fournisseursRepository.findAll().stream()
                .map(FournisseursDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("identifiants invalide");
        }
        fournisseursRepository.findByUuid(uuid);
    }
}
