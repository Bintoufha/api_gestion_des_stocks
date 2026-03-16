package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final Path rootLocation;

    public ImageServiceImpl(@Value("${app.upload.dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            // ✅ Crée le dossier si inexistant
            Files.createDirectories(rootLocation);
            System.out.println("✅ Répertoire de stockage créé : " + rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire pour stocker les fichiers", e);
        }
    }

    @Override
    public String save(MultipartFile file, String prefix) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Le fichier est vide");
            }

            // Vérification du type MIME
            String contentType = file.getContentType();
            if (!("image/png".equals(contentType) || "image/jpeg".equals(contentType))) {
                throw new IllegalArgumentException("Format non supporté. Seuls JPG, JPEG et PNG sont autorisés.");
            }

            // Récupérer l’extension du fichier original
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

                // Vérifier aussi l’extension pour plus de sécurité
                if (!(fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".jpeg"))) {
                    throw new IllegalArgumentException("Extension non supportée. Seuls .jpg, .jpeg et .png sont autorisés.");
                }
            }

            // Générer un nom unique : prefix_timestamp.extension
            String fileName = prefix + "_" + System.currentTimeMillis() + fileExtension;

            // Définir l’endroit de sauvegarde
            Path target = rootLocation.resolve(fileName);

            // Sauvegarder en remplaçant si existe déjà
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    @Override
    public void delete(String fileName) {
        try {
            Path filePath = load(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("✅ Fichier supprimé : " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression du fichier", e);
        }
    }

    /**
     * Méthode utilitaire pour vérifier si un fichier existe
     */
    public boolean exists(String fileName) {
        return Files.exists(load(fileName));
    }

    /**
     * Méthode utilitaire pour obtenir le répertoire de stockage
     */
    public Path getRootLocation() {
        return rootLocation;
    }
}
