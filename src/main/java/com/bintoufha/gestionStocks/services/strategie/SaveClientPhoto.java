package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service("clientStrategie")
@Slf4j
public class SaveClientPhoto implements Strategie<ClientsDto> {

    private UnsplashImageService unsplashImageService;
    private ClientService clientsService;

    @Autowired
    public SaveClientPhoto(UnsplashImageService unsplashImageService, ClientService clientsService) {
        this.unsplashImageService = unsplashImageService;
        this.clientsService = clientsService;
    }

    @Override
    public ClientsDto savePhoto(UUID uuid, InputStream photo, String titre) {
        ClientsDto clients =  clientsService.findByUuid(uuid);
        String urlPhotoClient = unsplashImageService.savePhoto(photo,titre);
        if (!StringUtils.hasLength(urlPhotoClient)){
            throw new InvalidOperationException("Erreur lors de enregistrement de image du client concerne",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clients.setPhotoClient(urlPhotoClient);
        return clientsService.save(clients);

    }
}
