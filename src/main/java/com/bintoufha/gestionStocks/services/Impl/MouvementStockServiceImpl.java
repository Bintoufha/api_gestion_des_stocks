package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.MouvementStocksDto;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.TypeMvtStocks;
import com.bintoufha.gestionStocks.repository.MouvementStockRepository;
import com.bintoufha.gestionStocks.services.ArticleService;
import com.bintoufha.gestionStocks.services.MouvementStockService;
import com.bintoufha.gestionStocks.validator.MouvementStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MouvementStockServiceImpl implements MouvementStockService {

    private MouvementStockRepository mvtRepository;
    private ArticleService articleService;

    public MouvementStockServiceImpl(MouvementStockRepository mvtRepository, ArticleService articleService) {
        this.mvtRepository = mvtRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(UUID uuidArticle) {
        if (uuidArticle == null){
            log.warn("identifiant de l'article est null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findByUuid(uuidArticle);
        return mvtRepository.stockReelArticle(uuidArticle);
    }

    @Override
    public List<MouvementStocksDto> mvtStockArticle(UUID uuidArticle) {
        return mvtRepository.findAllByArticle_Uuid(uuidArticle).stream()
                .map(MouvementStocksDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MouvementStocksDto entreeStock(MouvementStocksDto mvtStock) {
        return entreePositive(mvtStock,TypeMvtStocks.ENTREE);
    }

    @Override
    public MouvementStocksDto sortieStock(MouvementStocksDto mvtStock) {
        return sortieNegative(mvtStock, TypeMvtStocks.SORTIE);
    }

    @Override
    public MouvementStocksDto corectionStockPositive(MouvementStocksDto mvtStockPositive) {
        return entreePositive(mvtStockPositive,TypeMvtStocks.CORRECTION_POSITIVE) ;
    }

    @Override
    public MouvementStocksDto correctionStockNegative(MouvementStocksDto mvtStockNegative) {
        return sortieNegative(mvtStockNegative, TypeMvtStocks.CORRECTION_NEGATIVE);
    }

    private MouvementStocksDto entreePositive(MouvementStocksDto mvtPositive, TypeMvtStocks typeMvtStocks) {
        List<String> errors = MouvementStockValidator.validate(mvtPositive);
        if (!errors.isEmpty()) {
            log.warn("Article n'est pas valide");
            throw new InvalEntityException("le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_FOUND, errors);
        }
        mvtPositive.setQuantite(
                BigDecimal.valueOf(
                    Math.abs(mvtPositive.getQuantite().doubleValue())
                )
        );
        mvtPositive.setTypeMouvement(typeMvtStocks);
        return MouvementStocksDto.fromEntity(
                mvtRepository.save(MouvementStocksDto.toEntity(mvtPositive))
        );
    }
    private MouvementStocksDto sortieNegative(MouvementStocksDto mvtNegative, TypeMvtStocks typeMvtStocks) {
        List<String> errors = MouvementStockValidator.validate(mvtNegative);
        if (!errors.isEmpty()) {
            log.warn("Article n'est pas valide");
            throw new InvalEntityException("le mouvement de stock n'est pas valide", ErrorCodes.MVT_STK_NOT_FOUND, errors);
        }
        mvtNegative.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvtNegative.getQuantite().doubleValue()) * (-1)
                )
        );
        mvtNegative.setTypeMouvement(typeMvtStocks);
        return MouvementStocksDto.fromEntity(
                mvtRepository.save(MouvementStocksDto.toEntity(mvtNegative))
        );
    }
}
