package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.ligneVente.LigneVenteDto;
import com.bintoufha.gestionStocks.dto.mouvement.MouvementStocksDto;
import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.VenteMapper;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.*;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.services.VenteService;
import com.bintoufha.gestionStocks.services.strategieVente.CoutStrategie;
import com.bintoufha.gestionStocks.services.strategieVente.CoutStrategieFactory;
import com.bintoufha.gestionStocks.validator.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {

    private ArticleRepository articleRepository;
    private VenteRepository venteRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final LotStockRepository lotStockRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MouvementStockService mvtService;

    @Autowired
    public VenteServiceImpl(ArticleRepository articleRepository,
                            VenteRepository venteRepository,
                            LotStockRepository lotStockRepository,
                            EntrepriseRepository entrepriseRepository,
                            LigneVenteRepository ligneVenteRepository,
                            MouvementStockService mvtService) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.lotStockRepository = lotStockRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtService = mvtService;
    }

    @Override
    @Transactional
    public VentesDto save(VentesDto ventesDto) {
        List<String> errors = VenteValidator.validate(ventesDto);

        // 1) Validation basique
        if (ventesDto == null) {
            throw new InvalEntityException("Vente invalide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        UUID uuidEntreprise = ventesDto.getIdEntreprise();
        BigDecimal montantTotal = BigDecimal.ZERO;

        // 2) Boucler sur chaque ligne de vente
        for (LigneVenteDto ligneDto : ventesDto.getLigneVentes()) {
            UUID uuidArticle = ligneDto.getUuid();
            BigDecimal quantiteDemandee = ligneDto.getQte();
            String methodeValorisation = ligneDto.getVente().getValorisationMethod(); // FIFO / LIFO / PMP

            // 3) Récupérer les lots de l’article
            List<LotStocks> lots = lotStockRepository.findByIdEntrepriseAndArticles(
                    uuidEntreprise,
                    articleRepository.findByUuid(uuidArticle).orElseThrow(()->new InvalidOperationException("Aucun stock disponible pour l’article " + uuidArticle,
                            ErrorCodes.STOCK_NOT_FOUND))
            );

            // 4) Sélectionner la stratégie
            CoutStrategie strategie = CoutStrategieFactory.getStrategy(methodeValorisation);

            try {
                // 5) Calculer le coût total de la ligne
                BigDecimal totalCost = strategie.calculateCost(lots, quantiteDemandee);

                // 6) Déduire les quantités dans les lots
                decrementLotQuantities(lots, quantiteDemandee, methodeValorisation);

                // 7) Créer et enregistrer la ligne de vente
                LigneVente ligne = new LigneVente();
                ligne.setUuid(ventesDto.getUuid()); // sera mis après save de la vente
//                ligne.setArticles(ArticlesDto.toEntity(ligneDto.getArticles()));
                ligne.setQte(quantiteDemandee);
                ligne.setPrix(ligneDto.getPrix());
//                ligne.setPrixTotal(ligneDto.getPrixUnitaire().multiply(quantiteDemandee));
//                ligne.setPrixAchat(totalCost.divide(quantiteDemandee, 6, RoundingMode.HALF_UP));
                ligne.setIdEntreprise(uuidEntreprise);
//                ligne.setValorisationMethod(methodeCosting);

                ligneVenteRepository.save(ligne);
                updateMvStock(ligne);

                // 8) Ajouter au montant total de la vente
                montantTotal = montantTotal.add(ligne.getPrix().multiply(ligne.getQte()));

            } catch (Exception e) {
                throw new InvalidOperationException(e.getMessage(), ErrorCodes.STOCK_INSUFFISSANT);
            }
        }

        // 9) Enregistrer la vente globale
        Ventes vente = venteRepository.save(VenteMapper.toEntity(ventesDto));

        // 10) Mettre à jour le DTO
        ventesDto.setUuid(vente.getUuid());
        ventesDto.setMontantTotal(montantTotal);

        return ventesDto;

    }

    private void decrementLotQuantities(List<LotStocks> lots, BigDecimal quantite, String methode) {

        BigDecimal remaining = quantite;

        // Pour LIFO inverser les lots
        if (methode.equalsIgnoreCase("LIFO")) {
            Collections.reverse(lots);
        }

        for (LotStocks lot : lots) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal take = lot.getQteRestant().min(remaining);

            lot.setQteRestant(lot.getQteRestant().subtract(take));
            lotStockRepository.save(lot);

            remaining = remaining.subtract(take);
        }
    }

    @Override
    public VentesDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Ventes ID is NULL");
            return null;
        }
        return venteRepository.findByUuid(uuid)
                .map(VenteMapper::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByReference(String reference) {
        if (!StringUtils.hasLength(reference)) {
            log.error("Vente CODE est NULL");
            return null;
        }
        return venteRepository.findVentesByReference(reference)
                .map(VenteMapper::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException("Aucune vente client n'a ete trouve avec le CODE", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream()
                .map(VenteMapper::fromEntity)
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
    private void updateMvStock(LigneVente ligneVente) {
        MouvementStocksDto mvtStock = MouvementStocksDto.builder()
                .article(ligneVente.getArticles())
                .typeMouvement(TypeMvtStocks.SORTIE)
                .sourceMouvement(SourceMvtStocks.VENTE)
                .quantite(ligneVente.getQte())
                .idEntreprise(ligneVente.getIdEntreprise())
                .dateMvt(Instant.now())
                .build();
        mvtService.sortieStock(mvtStock);
    }
}
