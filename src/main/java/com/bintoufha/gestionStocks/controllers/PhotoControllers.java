package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.PhotoApi;
import com.bintoufha.gestionStocks.services.strategie.StrategiePhtotoContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class PhotoControllers implements PhotoApi {

    private StrategiePhtotoContext strategiePhtotoContext;

    public PhotoControllers(StrategiePhtotoContext strategiePhtotoContext) {

        this.strategiePhtotoContext = strategiePhtotoContext;
    }

    @Override
    public Object savePhoto(String context, UUID uuid, MultipartFile photo, String titre) throws IOException {
        return strategiePhtotoContext.savePhoto(context,uuid,photo.getInputStream(),titre);
    }
}
