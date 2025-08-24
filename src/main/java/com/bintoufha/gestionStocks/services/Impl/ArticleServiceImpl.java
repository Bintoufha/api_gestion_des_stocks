package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.services.ArticleService;
import com.bintoufha.gestionStocks.validator.ArticleValidator;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final DefaultErrorAttributes errorAttributes;
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, DefaultErrorAttributes errorAttributes) {
        this.articleRepository = articleRepository;
        this.errorAttributes = errorAttributes;
    }

    @Override
    public ArticlesDto save(ArticlesDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Cet article est invalide", dto);
            throw new InvalEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticlesDto.fromEntity(
                articleRepository.save(ArticlesDto.toEntity(dto)
                )
        );
    }

    @Override
    public ArticlesDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant article est introuvable");
            return null;
        }
        Optional<Articles> articles = articleRepository.findByUuid(uuid);

        return Optional.of(ArticlesDto.fromEntity(articles.get())).orElseThrow(() ->
                new EntityNoFoundException(
                        "Aucun article avec uuid" + uuid + "n'a été trouve dans la base de donnée",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
        );
    }

//    @Override
//    public ArticlesDto findByCodeArticle(String codeArticle) {
//        if (!StringUtils.isEmpty(codeArticle)) {
//            log.error("Code article est introuvable");
//            return null;
//        }
//
//        Optional<Articles> articles = articleRepository.findByCodeArticles(codeArticle);
//
//        return Optional.of(ArticlesDto.fromEntity(articles.get())).orElseThrow(() ->
//                new EntityNoFoundException(
//                        "Aucun article avec uuid" + codeArticle + "n'a été trouve dans la base de donnée",
//                        ErrorCodes.ARTICLE_NOT_FOUND
//                )
//        );
//    }

    @Override
    public List<ArticlesDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticlesDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant article est introuvable");
            return;
        }
        articleRepository.deleteByUuid(uuid);
    }
}
