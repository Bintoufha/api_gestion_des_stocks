package com.bintoufha.gestionStocks.services;

import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;


public interface ImageService {

    /**
     * Enregistre un fichier sur le disque
     * @param file Fichier à enregistrer
     * @param prefix Préfixe pour le nom du fichier
     * @return Nom du fichier enregistré
     */
    String save(MultipartFile file, String prefix);

    /**
     * Récupère le chemin complet du fichier
     * @param fileName Nom du fichier
     * @return Chemin complet
     */
    Path load(String fileName);

    /**
     * Supprime un fichier
     * @param fileName Nom du fichier à supprimer
     */
    void delete(String fileName);
}
