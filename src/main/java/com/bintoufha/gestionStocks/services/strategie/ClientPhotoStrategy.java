package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.ClientsDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.Clients;
import com.bintoufha.gestionStocks.repository.ClientsRepository;
import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class ClientPhotoStrategy implements PhotoStrategie<ClientsDto> {

    private final ClientsRepository clientsRepository;
    private final ImageService imageService;

    public ClientPhotoStrategy(ClientsRepository clientsRepository,
                               ImageService imageService) {
        this.clientsRepository = clientsRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean supports(String entityType) {
        return entityType.equalsIgnoreCase("clients");
    }

    @Override
    public ClientsDto savePhoto(UUID entiteUuid, MultipartFile file, String titre) {

        // 1. Trouver le client d'abord
        Clients client = clientsRepository.findByUuid(entiteUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Client non trouvé avec UUID: " + entiteUuid,
                        ErrorCodes.CLIENT_NOT_FOUND));

        // 2. Supprimer l'ancienne photo si elle existe
        String oldPhoto = client.getPhotoClient();
        if (StringUtils.hasLength(oldPhoto)) {
            try {
                imageService.delete(oldPhoto);
            } catch (Exception e) {
                // Loguer l'erreur mais continuer
                log.warn("Impossible de supprimer l'ancienne photo: {}", oldPhoto, e);
            }
        }

        // 3. Sauvegarder la nouvelle image
        String fileName = imageService.save(file, "client_" + entiteUuid);

        if (!StringUtils.hasLength(fileName)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de l'image",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mettre à jour et sauvegarder
        client.setPhotoClient(fileName);
        Clients savedClient = clientsRepository.save(client);

        return ClientsDto.fromEntity(savedClient);
    }
}
