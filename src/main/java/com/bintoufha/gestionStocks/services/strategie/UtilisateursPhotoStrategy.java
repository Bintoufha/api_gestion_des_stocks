package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.UtilisateurMapper;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class UtilisateursPhotoStrategy implements PhotoStrategie<UtilisateurSaveDto> {

    private final UtilisateursRepository utilisateursRepository;
    private final ImageService imageService;

    public UtilisateursPhotoStrategy(UtilisateursRepository utilisateursRepository,
                                     ImageService imageService) {
        this.utilisateursRepository = utilisateursRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean supports(String entityType) {
        return entityType.equalsIgnoreCase("utilisateurs");
    }

    @Override
    public UtilisateurSaveDto savePhoto(UUID entiteUuid, MultipartFile file, String titre) {

        // 1. Trouver le client d'abord
        Utilisateurs utilisateurs = utilisateursRepository.findByUuid(entiteUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "entreprises non trouvé avec UUID: " + entiteUuid,
                        ErrorCodes.UTILISATEUR_NOT_FOUND));

        // 2. Supprimer l'ancienne photo si elle existe
        String oldPhoto = utilisateurs.getPhotoUtilisateurs();
        if (StringUtils.hasLength(oldPhoto)) {
            try {
                imageService.delete(oldPhoto);
            } catch (Exception e) {
                // Loguer l'erreur mais continuer
                log.warn("Impossible de supprimer l'ancienne photo: {}", oldPhoto, e);
            }
        }

        // 3. Sauvegarder la nouvelle image
        String fileName = imageService.save(file, "utilisateurs_" + entiteUuid);

        if (!StringUtils.hasLength(fileName)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de l'image",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mettre à jour et sauvegarder
        utilisateurs.setPhotoUtilisateurs(fileName);
        Utilisateurs savedUtilisateurs = utilisateursRepository.save(utilisateurs);

        return UtilisateurMapper.fromEntity(savedUtilisateurs);
    }
}
