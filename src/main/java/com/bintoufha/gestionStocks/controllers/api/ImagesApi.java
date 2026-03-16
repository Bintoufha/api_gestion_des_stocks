package com.bintoufha.gestionStocks.controllers.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@RestController
public interface ImagesApi {

    // 📥 Upload photo pour une entité
    @PostMapping(
       value= APP_ROOT + "/image/upload/{entiteUuid}/{titre}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<String> savePhoto(@PathVariable("entiteUuid") UUID entiteUuid,
                                     @RequestPart("file") MultipartFile file,
                                     @PathVariable("titre") String titre);

    // 📤 Récupérer une photo
    @GetMapping(APP_ROOT + "/image/recupere/{fileName}")
    ResponseEntity<Resource> load(@PathVariable String fileName);

    @DeleteMapping(APP_ROOT + "/image/supprime/{uuidPhoto}")
    ResponseEntity<String> delete(@PathVariable("uuidPhoto") String fileName);

}
