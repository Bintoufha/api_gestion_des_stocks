package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.services.ArticleService;
import com.bintoufha.gestionStocks.services.UnsplashImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service("articleStrategie")
@Slf4j
public class SaveArticlePhoto implements Strategie<ArticlesDto> {

    private UnsplashImageService unsplashImageService;
    private ArticleService articleService;

    @Autowired
    public SaveArticlePhoto(UnsplashImageService unsplashImageService, ArticleService articleService) {
        this.unsplashImageService = unsplashImageService;
        this.articleService = articleService;
    }

    @Override
    public ArticlesDto savePhoto(UUID uuid, InputStream photo, String titre) {
        ArticlesDto articles = articleService.findByUuid(uuid);
        String urlPhotoArticle = unsplashImageService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhotoArticle)){
            throw new InvalidOperationException("Erreur lors de enregistrement de image de article",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articles.setPhotoArticle(urlPhotoArticle);
        return articleService.save(articles);
    }
}
