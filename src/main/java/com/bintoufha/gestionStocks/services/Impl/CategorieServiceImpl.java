package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.categorie.CategorieListDto;
import com.bintoufha.gestionStocks.dto.categorie.CategorieSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.CategorieMapper;
import com.bintoufha.gestionStocks.model.Articles;
import com.bintoufha.gestionStocks.model.Categories;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.CategorieRepository;
import com.bintoufha.gestionStocks.services.CategorieService;
import com.bintoufha.gestionStocks.validator.CategorieValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    private final DefaultErrorAttributes errorAttributes;
    private final CategorieRepository categorieRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategorieServiceImpl(
            CategorieRepository categorieRepository, DefaultErrorAttributes errorAttributes, ArticleRepository articleRepository
    ) {
        this.categorieRepository = categorieRepository;
        this.articleRepository = articleRepository;
        this.errorAttributes = new DefaultErrorAttributes();
    }

    @Override
    public CategorieSaveDto save(CategorieSaveDto dto) {
        List<String> errors = CategorieValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Cette categorie est invalide", dto);
            throw new InvalEntityException(
                    "cette categorie est invalide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }

        // 3️⃣ Mapper DTO → Entity
        Categories categorie = CategorieMapper.toEntity(dto);

        // 4️⃣ Sauvegarder
        Categories saved = categorieRepository.save(categorie);

        // 5️⃣ Retourner DTO propre
        return CategorieMapper.toSaveDto(saved);

    }

    @Override
    public CategorieListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant article est introuvable");
            throw new IllegalArgumentException("UUID de l'article ne peut pas être null");
        }

        // Récupération de la categorie ou exception si non trouvé
        Categories categories = categorieRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun article avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return CategorieMapper.toListDto(categories);
    }

    @Override
    public CategorieListDto findByCode(String code) {
        return null;
    }

    @Override
    public List<CategorieListDto> findAll() {
        return categorieRepository.findAll()
                .stream()
                .map(categories -> {
                    // ou via TarificationService si besoin
                    return CategorieMapper.toListDto(categories);
                })
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("Category ID is null");
            return;
        }
        List<Articles> articles = articleRepository.findAllByCategorieUuid(uuid);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette categorie qui est deja utilise",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categorieRepository.deleteById(uuid);
    }
}
