package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.ArticlesDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeClientsDto;
import com.bintoufha.gestionStocks.dto.LigneCommandeFournisseursDto;
import com.bintoufha.gestionStocks.dto.LigneVenteDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Articles", description = "API de gestion des articles")
@RestController
@RequestMapping(APP_ROOT + "/articles")

public interface ArticlesApi {


    @PostMapping(value = APP_ROOT + "/articles/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Enregistrer un article",
            description = "Cette méthode permet d'enregistrer ou de modifier un article dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Créé ou modifié un article")
    })
    ArticlesDto save(@RequestBody ArticlesDto dto);

    @GetMapping(value = APP_ROOT + "/articles/recherche/{uuidArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher un article par UUID",
            description = "Cette méthode permet de rechercher un article par son identifiant UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article trouvé"),
            @ApiResponse(responseCode = "404", description = "Aucun article trouvé avec l'identifiant fourni")
    })
    ArticlesDto findByUUID(@PathVariable("uuidArticle") UUID uuid);

//    @GetMapping(value = APP_ROOT + "/article/code_article/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Rechercher un article par code",
//            description = "Cette méthode permet de rechercher un article par son code")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Article trouvé"),
//            @ApiResponse(responseCode = "404", description = "Aucun article trouvé avec le code fourni")
//    })
//    ArticlesDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = APP_ROOT + "/articles/allArticle", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lister tous les articles",
            description = "Cette méthode permet de lister tous les articles enregistrés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles (vide si aucun)")
    })
    List<ArticlesDto> findAll();

    @GetMapping(value = APP_ROOT + "/articles/historique/vente/{uuidArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVente(@PathVariable("uuidArticle") UUID uuidArticle);

    @GetMapping(value = APP_ROOT + "/articles/historique/commandeClient/{uuidArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientsDto> findHistoriqueCommandeCLients(@PathVariable("uuidArticle") UUID uuidArticle);

    @GetMapping(value = APP_ROOT + "/articles/historique/commandeFournisseur/{uuidArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseursDto> findHistoriqueCommandeFournisseurs(@PathVariable("uuidArticle") UUID uuidArticle);

    @GetMapping(value = APP_ROOT + "/articles/filter/categorie/{uuidCategorie}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticlesDto> findAllArticleByCategorieUuid (@PathVariable("uuidCategorie") UUID uuidCategorie);

    @DeleteMapping(value = APP_ROOT + "/articles/supprimerArticle/{uuidArticles}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Supprimer un article",
            description = "Cette méthode permet de supprimer un article par UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article supprimé"),
            @ApiResponse(responseCode = "400", description = "Impossible de supprimer l'article")
    })
    void delete(@PathVariable("uuidArticles") UUID uuid);
}
