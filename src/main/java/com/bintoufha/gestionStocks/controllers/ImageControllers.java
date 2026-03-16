package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.controllers.api.ImagesApi;
import com.bintoufha.gestionStocks.services.ImageService;
import com.bintoufha.gestionStocks.services.strategie.PhotoStrategyFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
public class ImageControllers implements ImagesApi {

    private final PhotoStrategyFactory strategieFactory;
    private final ImageService photoService;

    public ImageControllers(PhotoStrategyFactory strategieFactory, ImageService photoService) {
        this.strategieFactory = strategieFactory;
        this.photoService = photoService;
    }

    @Override
    public ResponseEntity<String> savePhoto(UUID entiteUuid, MultipartFile file, String titre) {
        String fileName = strategieFactory.getStrategie(titre).savePhoto(entiteUuid, file,titre).toString();
        return ResponseEntity.ok("✅ Photo enregistrée pour " + titre + " : " + fileName);
    }

    @Override
    public ResponseEntity<Resource> load(String fileName) {
        try {
            Path filePath = photoService.load(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            // ⚠️ Ici on suppose que c’est une image JPEG (tu peux améliorer avec détection du type MIME)
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(String fileName) {
        photoService.delete(fileName);
        return ResponseEntity.ok("🗑️ Photo supprimée : " + fileName);
    }
//
//    // 📤 Récupérer une photo
//    @GetMapping("/{fileName}")
//    public ResponseEntity<Resource> getPhoto(@PathVariable String fileName) {
//        try {
//            Path filePath = photoService.load(fileName);
//            Resource resource = new UrlResource(filePath.toUri());
//
//            // ⚠️ Ici on suppose que c’est une image JPEG (tu peux améliorer avec détection du type MIME)
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_JPEG)
//                    .body(resource);
//
//        } catch (MalformedURLException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // ❌ Supprimer une photo
//    @DeleteMapping("/{fileName}")
//    public ResponseEntity<String> deletePhoto(@PathVariable String fileName) {
//        photoService.delete(fileName);
//        return ResponseEntity.ok("🗑️ Photo supprimée : " + fileName);
//    }
}
