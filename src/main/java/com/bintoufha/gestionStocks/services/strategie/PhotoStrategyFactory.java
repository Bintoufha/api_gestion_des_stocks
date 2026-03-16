package com.bintoufha.gestionStocks.services.strategie;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhotoStrategyFactory {

    private final List<PhotoStrategie> strategies;

    // Spring injecte toutes les implémentations de PhotoStrategie
    public PhotoStrategyFactory(List<PhotoStrategie> strategies) {
        this.strategies = strategies;

    }

    // Retourne la stratégie qui correspond au type d’entité
    public PhotoStrategie getStrategie(String entityType) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(entityType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("❌ Type d’entité non supporté: " + entityType));
    }
}
