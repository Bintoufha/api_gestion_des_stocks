package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class ArticlesPhotoStrategy implements PhotoStrategie<ArticlesDto> {

    private final ArticleRepository articleRepository;
    private final ImageService imageService;

    public ArticlesPhotoStrategy(ArticleRepository articleRepository,
                                 ImageService imageService) {
        this.articleRepository = articleRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean supports(String entityType) {
        return entityType.equalsIgnoreCase("articles");
    }

    @Override
    public ArticlesDto savePhoto(UUID entiteUuid, MultipartFile file, String titre) {

        // 1. Trouver le client d'abord
        Articles articles = articleRepository.findByUuid(entiteUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "article non trouvé avec UUID: " + entiteUuid,
                        ErrorCodes.ARTICLE_NOT_FOUND));

        // 2. Supprimer l'ancienne photo si elle existe
        String oldPhoto = articles.getPhotoArticle();
        if (StringUtils.hasLength(oldPhoto)) {
            try {
                imageService.delete(oldPhoto);
            } catch (Exception e) {
                // Loguer l'erreur mais continuer
                log.warn("Impossible de supprimer l'ancienne photo: {}", oldPhoto, e);
            }
        }

        // 3. Sauvegarder la nouvelle image
        String fileName = imageService.save(file, "articles_" + entiteUuid);

        if (!StringUtils.hasLength(fileName)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de l'image",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // 4. Mettre à jour et sauvegarder
        articles.setPhotoArticle(fileName);
        Articles savedArticles = articleRepository.save(articles);

        return ArticlesDto.fromEntity(savedArticles);
    }
}
