package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class EntreprisePhotoStrategy implements PhotoStrategie<EntrepriseDto> {

    private final EntrepriseRepository entrepriseRepository;
    private final ImageService imageService;

    public EntreprisePhotoStrategy(EntrepriseRepository entrepriseRepository,
                                   ImageService imageService) {
        this.entrepriseRepository = entrepriseRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean supports(String entityType) {
        return entityType.equalsIgnoreCase("entreprises");
    }

    @Override
    public EntrepriseDto savePhoto(UUID entiteUuid, MultipartFile file, String titre) {

        // 1. Trouver le client d'abord
        Entreprises entreprises = entrepriseRepository.findByUuid(entiteUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "entreprises non trouvé avec UUID: " + entiteUuid,
                        ErrorCodes.ENTREPRISE_NOT_FOUND));

        // 2. Supprimer l'ancienne photo si elle existe
        String oldPhoto = entreprises.getPhotoEntreprise();
        if (StringUtils.hasLength(oldPhoto)) {
            try {
                imageService.delete(oldPhoto);
            } catch (Exception e) {
                // Loguer l'erreur mais continuer
                log.warn("Impossible de supprimer l'ancienne photo: {}", oldPhoto, e);
            }
        }

        // 3. Sauvegarder la nouvelle image
        String fileName = imageService.save(file, "entreprises_" + entiteUuid);

        if (!StringUtils.hasLength(fileName)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de l'image",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mettre à jour et sauvegarder
        entreprises.setPhotoEntreprise(fileName);
        Entreprises savedEntreprises = entrepriseRepository.save(entreprises);

        return EntrepriseDto.fromEntity(savedEntreprises);
    }
}
