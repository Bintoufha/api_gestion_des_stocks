package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.FournisseurMapper;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import com.bintoufha.gestionStocks.repository.FournisseursRepository;
import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class FournisseursPhotoStrategy implements PhotoStrategie<FournisseurSaveDto> {

    private final FournisseursRepository fournisseursRepository;
    private final ImageService imageService;

    public FournisseursPhotoStrategy(FournisseursRepository fournisseursRepository,
                                     ImageService imageService) {
        this.fournisseursRepository = fournisseursRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean supports(String entityType) {
        return entityType.equalsIgnoreCase("fournisseurs");
    }

    @Override
    public FournisseurSaveDto savePhoto(UUID entiteUuid, MultipartFile file, String titre) {

        // 1. Trouver le client d'abord
        Fournisseurs fournisseurs = fournisseursRepository.findByUuid(entiteUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "fournisseurs non trouvé avec UUID: " + entiteUuid,
                        ErrorCodes.FOURNISSEUR_NOT_FOUND));

        // 2. Supprimer l'ancienne photo si elle existe
        String oldPhoto = fournisseurs.getPhotoFournisseurs();
        if (StringUtils.hasLength(oldPhoto)) {
            try {
                imageService.delete(oldPhoto);
            } catch (Exception e) {
                // Loguer l'erreur mais continuer
                log.warn("Impossible de supprimer l'ancienne photo: {}", oldPhoto, e);
            }
        }

        // 3. Sauvegarder la nouvelle image
        String fileName = imageService.save(file, "fournisseurs_" + entiteUuid);

        if (!StringUtils.hasLength(fileName)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de l'image",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mettre à jour et sauvegarder
        fournisseurs.setPhotoFournisseurs(fileName);
        Fournisseurs savedFournisseurs = fournisseursRepository.save(fournisseurs);

        return FournisseurMapper.toSaveDto(savedFournisseurs);
    }
}
