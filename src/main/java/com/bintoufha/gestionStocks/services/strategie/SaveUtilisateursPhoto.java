package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service("utilisateurStrategie")
@Slf4j
public class SaveUtilisateursPhoto implements Strategie<UtilisateursDto> {

    private UnsplashImageService unsplashImageService;
    private UtilisateurService utilisateurService;

    @Override
    public UtilisateursDto savePhoto(UUID uuid, InputStream photo, String titre) {
        UtilisateursDto utilisateurs = utilisateurService.findByUuid(uuid);
        String urlPhotoUtilisateurs = unsplashImageService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhotoUtilisateurs)){
            throw new InvalidOperationException("Erreur lors de enregistrement de image de l'entreprise",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        utilisateurs.setPhotoUtilisateurs(urlPhotoUtilisateurs);
        return utilisateurService.save(utilisateurs);
    }
}
