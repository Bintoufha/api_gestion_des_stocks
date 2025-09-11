package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.LigneVenteDto;
import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import com.bintoufha.gestionStocks.dto.VentesDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.ArticleRepository;
import com.bintoufha.gestionStocks.repository.LigneVenteRepository;
import com.bintoufha.gestionStocks.repository.VenteRepositry;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.services.VenteService;
import com.bintoufha.gestionStocks.validator.VenteValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Slf4j
public class  VenteServiceImpl implements VenteService {

    private ArticleRepository articleRepository;
    private VenteRepositry venteRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MouvementStockService mvtService;

    @Autowired
    public VenteServiceImpl(ArticleRepository articleRepository,
                            VenteRepositry venteRepository, LigneVenteRepository ligneVenteRepository, MouvementStockService mvtService) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtService = mvtService;
    }

    @Override
    public VentesDto save(VentesDto ventesDto) {
        List<String> errors = VenteValidator.validate(ventesDto);
        if (!errors.isEmpty()){
            log.warn("vente n'est pas valide");
            throw new InvalEntityException("l'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID,errors);
        }

        List<String> articleErrors = new ArrayList<>();
        ventesDto.getLigneVentes().forEach(ligneVenteDto->{
            Optional<Articles> articles = articleRepository.findByUuid(ligneVenteDto.getArticles().getUuid());
            if (articles.isEmpty()){
                articleErrors.add("Aucun article avec identidiant "+ligneVenteDto.getArticles().getUuid()+" n'a été trouve");
            }
        });
        if (articleErrors.isEmpty()){
            log.warn("Aucun article n'a ete trouve");
            throw new InvalEntityException("Un ou plusieur article n'ont pas ete trouve",
                    ErrorCodes.VENTE_NOT_FOUND,errors);
        }
        Ventes saveVente = venteRepository.save(VentesDto.toEntity(ventesDto));

        ventesDto.getLigneVentes().forEach(ligneVenteDto->{
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(saveVente);
            ligneVenteRepository.save(ligneVente);
            updateMvStock(ligneVente);
        });
        return VentesDto.fromEntity(saveVente);
    }

    @Override
    public VentesDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Ventes ID is NULL");
            return null;
        }
        return venteRepository.findByUuid(uuid)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByReference(String reference) {
        if (!StringUtils.hasLength(reference)) {
            log.error("Vente CODE est NULL");
            return null;
        }
        return venteRepository.findVentesByReference(reference)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException("Aucune vente client n'a ete trouve avec le CODE",ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if (id == null) {
            log.error("Vente ID is NULL");
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVente_Uuid(uuid);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une vente ...",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        venteRepository.deleteByUuid(uuid);
    }

    // methode pour mettre a jour le stock pour la vente
    private void updateMvStock(LigneVente ligneVente ){
            MouvementStocksDto mvtStock = MouvementStocksDto.builder()
                    .article(ligneVente.getArticles())
                    .typeMouvement(TypeMvtStocks.SORTIE)
                    .sourceMouvement(SourceMvtStocks.VENTE )
                    .quantite(ligneVente.getQte())
                    .idEntreprise(ligneVente.getIdEntreprise())
                    .dateMvt(Instant.now())
                    .build();
            mvtService.sortieStock(mvtStock);
    }
}
