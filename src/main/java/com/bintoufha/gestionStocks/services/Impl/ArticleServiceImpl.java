package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.article.ArticleListDto;
import com.bintoufha.gestionStocks.dto.article.ArticleSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeClient.LigneCommandeClientSaveDto;
import com.bintoufha.gestionStocks.dto.ligneCommandeFournisseurs.LigneCommandeFournisseurSaveDto;
import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.ArticleMapper;
import com.bintoufha.gestionStocks.mapper.LigneCommandeClientMapper;
import com.bintoufha.gestionStocks.mapper.LigneCommandeFournisseurMapper;
import com.bintoufha.gestionStocks.mapper.LigneVenteMapper;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.*;
import com.bintoufha.gestionStocks.services.ArticleService;
import com.bintoufha.gestionStocks.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final CategorieRepository categoriesRepository;
    private ArticleRepository articleRepository;
    private LigneVenteRepository venteRepository;
    private LigneCommandeFournisseursRepository ligneCommandeFournisseursRepository;
    private LigneCommandeClientsRepository ligneCommandeClientsRepository;

    public ArticleServiceImpl(CategorieRepository categoriesRepository, ArticleRepository articleRepository,
                              LigneVenteRepository venteRepository,
                              LigneCommandeFournisseursRepository ligneCommandeFournisseursRepository,
                              LigneCommandeClientsRepository ligneCommandeClientsRepository
    )
    {
        this.categoriesRepository = categoriesRepository;
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.ligneCommandeFournisseursRepository = ligneCommandeFournisseursRepository;
        this.ligneCommandeClientsRepository = ligneCommandeClientsRepository;
    }

    public ArticleSaveDto save(ArticleSaveDto dto) {

        // 1️⃣ Validation
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article invalide {}", dto);
            throw new InvalEntityException(
                    "L'article n'est pas valide",
                    ErrorCodes.ARTICLE_NOT_VALID,
                    errors
            );
        }

        // 2️⃣ Charger la catégorie
        Categories categorie = categoriesRepository.findByUuid(dto.getCategorieUuid())
                .orElseThrow(() -> new EntityNoFoundException(
                        "Catégorie introuvable",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));

        // 3️⃣ Mapper DTO → Entity
        Articles article = ArticleMapper.toEntity(dto, categorie);

        // 4️⃣ Sauvegarder
        Articles saved = articleRepository.save(article);

        // 5️⃣ Retourner DTO propre
        return ArticleMapper.toSaveDto(saved);
    }


    @Override
    public ArticleListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant article est introuvable");
            throw new IllegalArgumentException("UUID de l'article ne peut pas être null");
        }

        // Récupération de l'article ou exception si non trouvé
        Articles article = articleRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun article avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return ArticleMapper.toListDto(article, article.getPrixUnitaireArticle());
    }


    @Override
    public List<ArticleListDto> findAll() {

        return articleRepository.findAll()
                .stream()
                .map(article -> {
                    BigDecimal prix = article.getPrixUnitaireArticle();
                    // ou via TarificationService si besoin
                    return ArticleMapper.toListDto(article, prix);
                })
                .toList();
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
//@Override
//public List<ArticleListDto> findAll() {

//    // On récupère l’année envoyée par l’intercepteur Angular dans le header X-ANNEE
//    // Exemple : "2024-2025"
//    String anneeFromMdc = MDC.get("annee");
//
//    // Si aucune année n’est trouvée → on retourne tous les articles sans filtrage
//    if (anneeFromMdc == null || anneeFromMdc.isEmpty()) {
//        return articleRepository.findAll()
//                .stream()
//                .map(ArticlesDto::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    // L'année est envoyée sous le format "2024-2025"
//    // Nous n’utilisons que la première année (2024)
//    String[] parts = anneeFromMdc.split("-");
//
//    // Année de début = première partie
//    int startYear = Integer.parseInt(parts[0]);
//
//    // -------------------------------------------------------------------
//    // Construire le range de dates pour filtrer sur l’année civile :
//    //  - Début : 1 janvier de l’année (00:00:00.000)
//    //  - Fin   : 31 décembre de la même année (23:59:59.999)
//    // -------------------------------------------------------------------
//
//    // Début : 1er janvier
//    Calendar calStart = Calendar.getInstance();
//    calStart.set(startYear, Calendar.JANUARY, 1, 0, 0, 0);
//    calStart.set(Calendar.MILLISECOND, 0);
//    Date dateStart = calStart.getTime();
//
//    // Fin : 31 décembre
//    Calendar calEnd = Calendar.getInstance();
//    calEnd.set(startYear, Calendar.DECEMBER, 31, 23, 59, 59);
//    calEnd.set(Calendar.MILLISECOND, 999);
//    Date dateEnd = calEnd.getTime();
//
//    // -------------------------------------------------------------------
//    // On exécute la requête Spring Data :
//    // SELECT * FROM articles WHERE creationDate BETWEEN dateStart AND dateEnd
//    // -------------------------------------------------------------------
//    return articleRepository.findByCreationDateBetween(dateStart, dateEnd)
//            .stream()
//            .map(ArticlesDto::fromEntity)
//            .collect(Collectors.toList());
//}



    // @Override
    // public List<ArticlesDto> findAll() {
    //     if (year != null) {
    //     Date[] bounds = YearUtil.toDateRange(year); // Convertit 2025-2026 en Date[]
    //     return articleRepository.findByCreationDateBetween(bounds[0])
    //                              .stream()
    //                              .map(ArticlesDto::fromEntity)
    //                              .collect(Collectors.toList());
    // }
//     // Si aucune année sélectionnée, retourne tout
//     return articleRepository.findAll()
//                              .stream()
//                              .map(ArticlesDto::fromEntity)
//                              .collect(Collectors.toList());
//         // return articleRepository.findAll().stream()
//         //         .map(ArticlesDto::fromEntity)
//         //         .collect(Collectors.toList());
    // }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(UUID uuidArticle) {
        return venteRepository.findAllByUuid(uuidArticle).stream()
                .map(LigneVenteMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientSaveDto> findHistoriqueCommandeCLients(UUID uuidArticle) {
        return ligneCommandeClientsRepository.findCommandeClientsByUuid(uuidArticle).stream()
                .map(LigneCommandeClientMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurSaveDto> findHistoriqueCommandeFournisseurs(UUID uuidArticle) {
        return ligneCommandeFournisseursRepository.findAllByArticles_Uuid(uuidArticle).stream()
                .map(LigneCommandeFournisseurMapper::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ArticlesDto> findAllArticleByCategorieUuid(UUID uuidCategorie) {
//        return articleRepository.findAllByCategorieUuid(uuidCategorie).stream()
//                .map(ArticlesDto::fromEntity)
//                .collect(Collectors.toList());
//    }

    @Transactional // ✅ Ici
    @Override
    public void delete(UUID uuid) {
        if (uuid == null) {
            log.error("identifiant article est introuvable");
            return;
        }
        List<LigneCommandeClients> ligneCommandeClients = ligneCommandeClientsRepository.findCommandeClientsByUuid(uuid);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneCommandeFournisseurs> ligneCommandeFournisseurs = ligneCommandeFournisseursRepository.findAllByArticles_Uuid(uuid);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneVente> ligneVentes = venteRepository.findAllByUuid(uuid);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteByUuid(uuid);
    }


    /*
@Transactional
public ArticlesDto save(ArticlesDto dto) {
    // 1) Validation
    List<String> errors = ArticleValidator.validate(dto);
    if (!errors.isEmpty()) {
        log.error("Article invalide {}", dto);
        throw new InvalEntityException("L'article n'est pas valide", 
            ErrorCodes.ARTICLE_NOT_VALID, errors);
    }

    // 2) Enregistrer l’article
    Articles savedArticle = articleRepository.save(ArticlesDto.toEntity(dto));

    // 3) Créer un lot initial pour cet article
    LotStocksDto lotDto = new LotStocksDto();
    lotDto.setUuid(UUID.randomUUID());
    lotDto.setIdEntreprise(dto.getIdEntreprise());
    lotDto.setArticles(savedArticle);
    lotDto.setQteCommande(BigDecimal.ZERO);
    lotDto.setQteRestant(BigDecimal.ZERO);
    lotDto.setPrixUnitaire(BigDecimal.ZERO);

    lotStockRepository.save(LotStocksDto.toEntity(lotDto));

    // 4) Créer un mouvement de stock initial
    MouvementStocksDto mvDto = new MouvementStocksDto();
    mvDto.setUuid(UUID.randomUUID());
    mvDto.setArticle(savedArticle);
    mvDto.setDateMvt(Instant.now());
    mvDto.setTypeMouvement(TypeMvtStocks.ENTREE);
    mvDto.setSourceMouvement(SourceMvtStocks.ENREGISTREMENT_ARTICLE);
    mvDto.setQuantite(BigDecimal.ZERO);
    mvDto.setMotif("Création d’article");
    mvDto.setIdEntreprise(dto.getIdEntreprise());

    mouvementStockRepository.save(MouvementStocksDto.toEntity(mvDto));

    // 5) Retourner le DTO article
    return ArticlesDto.fromEntity(savedArticle);
}



    */ 
}
