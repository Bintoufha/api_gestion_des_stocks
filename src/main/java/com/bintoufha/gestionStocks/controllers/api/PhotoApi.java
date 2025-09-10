package com.bintoufha.gestionStocks.controllers.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

public interface PhotoApi {
    @PostMapping(APP_ROOT + "/photo/{uuid}/{titre}/{context}")
    Object savePhoto(String context, UUID uuid, @RequestPart("file") MultipartFile photo, String titre) throws IOException;
}
