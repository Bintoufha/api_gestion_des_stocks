package com.bintoufha.gestionStocks.services.strategie;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStrategie<T> {

    // Vérifie si la stratégie correspond au type d’entité
    boolean supports(String entityType);

    // Associe une photo à l’entité
    T savePhoto(UUID entiteUuid, MultipartFile file,String titre);
}