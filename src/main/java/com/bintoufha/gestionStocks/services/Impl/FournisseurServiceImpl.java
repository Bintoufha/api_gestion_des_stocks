package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurListDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.FournisseurMapper;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import com.bintoufha.gestionStocks.repository.CommandeFournisseursRepository;
import com.bintoufha.gestionStocks.repository.FournisseursRepository;
import com.bintoufha.gestionStocks.services.FournisseurService;
import org.springframework.transaction.annotation.Transactional;
import com.bintoufha.gestionStocks.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FournisseurServiceImpl  implements FournisseurService {

    private final DefaultErrorAttributes errorAttributes;
    private FournisseursRepository fournisseursRepository;
    private CommandeFournisseursRepository commandeFournisseursRepository;

    public FournisseurServiceImpl(
        DefaultErrorAttributes errorAttributes,
        CommandeFournisseursRepository commandeFournisseursRepository, 
        FournisseursRepository fournisseursRepository) {
        this.fournisseursRepository = fournisseursRepository;
        this.errorAttributes = errorAttributes;
        this.commandeFournisseursRepository = commandeFournisseursRepository;
    }

    @Override
    public FournisseurSaveDto save(FournisseurSaveDto fournisseursDto) {
        List<String> errors = FournisseurValidator.validate(fournisseursDto);
        if (!errors.isEmpty()) {
            log.error("donnee non valide");
            throw new InvalEntityException("tout les information du fournisseur n'ont pas ete renseigner",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND, errors);
        }

        // 3️⃣ Mapper DTO → Entity
        Fournisseurs fournisseurs = FournisseurMapper.toEntity(fournisseursDto);

        // 4️⃣ Sauvegarder
        Fournisseurs saved = fournisseursRepository.save(fournisseurs);

        // 5️⃣ Retourner DTO propre
        return FournisseurMapper.toSaveDto(saved);
    }

    @Override
    public FournisseurListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant client est introuvable");
        }


        // Récupération de la categorie ou exception si non trouvé
        Fournisseurs fournisseurs = fournisseursRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun fournisseurs avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return FournisseurMapper.toListDto(fournisseurs);
    }

    @Override
    public List<FournisseurListDto> findAll() {
        return fournisseursRepository.findAll()
                .stream()
                .map(fournisseurs -> {
                    // ou via TarificationService si besoin
                    return FournisseurMapper.toListDto(fournisseurs);
                })
                .toList();
    }

    
    @Override
   @Transactional
    public void deleteByUuid(UUID uuid) {

        if (uuid == null) {
            log.error("Identifiant invalide");
            throw new EntityNoFoundException(
                    "L'UUID fourni est null",
                    ErrorCodes.FOURNISSEUR_NOT_VALID
            );
        }

        // Vérifier si le fournisseurs existe
        Fournisseurs fournisseurs = fournisseursRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "fournisseurs avec UUID " + uuid + " non trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));

        // Vérifier si le fournisseurs a des commandes
        List<CommandeFournisseurs> commandeFournisseur = commandeFournisseursRepository.findAllByFournisseursUuid(uuid);
        if (!commandeFournisseur.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }

        // Supprimer le foournisseur (CORRECT)
        fournisseursRepository.delete(fournisseurs);
    }

}
