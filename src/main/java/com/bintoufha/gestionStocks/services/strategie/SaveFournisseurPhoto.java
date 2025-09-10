package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.FournisseursDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service("fournisseurStrategie")
@Slf4j
public class SaveFournisseurPhoto implements Strategie<FournisseursDto> {
    private UnsplashImageService unsplashImageService;
    private FournisseurService fournisseurService;

    @Autowired
    public SaveFournisseurPhoto(UnsplashImageService unsplashImageService, FournisseurService fournisseurService) {
        this.unsplashImageService = unsplashImageService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseursDto savePhoto(UUID uuid, InputStream photo, String titre) {
        FournisseursDto fournisseur = fournisseurService.findByUuid(uuid);
        String urlPhotoFournisseurs = unsplashImageService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhotoFournisseurs)){
            throw new InvalidOperationException("Erreur lors de enregistrement de image de l'entreprise",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseur.setPhotoFournisseurs(urlPhotoFournisseurs);
        return fournisseurService.save(fournisseur);
    }
}
