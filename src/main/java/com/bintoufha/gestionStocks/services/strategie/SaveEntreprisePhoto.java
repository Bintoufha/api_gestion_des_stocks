package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.services.EntrepriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service("entrepriseStrategie")
@Slf4j
public class SaveEntreprisePhoto implements Strategie<EntrepriseDto>{

    private UnsplashImageService unsplashImageService;
    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(UnsplashImageService unsplashImageService, EntrepriseService entrepriseService) {
        this.unsplashImageService = unsplashImageService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(UUID uuid, InputStream photo, String titre) {
        EntrepriseDto entreprise = entrepriseService.findByUuid(uuid);
        String urlPhotoEntreprise =  unsplashImageService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhotoEntreprise)){
            throw new InvalidOperationException("Erreur lors de enregistrement de image de l'entreprise",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entreprise.setPhotoEntreprise(urlPhotoEntreprise);
        return entrepriseService.save(entreprise);
    }
}
