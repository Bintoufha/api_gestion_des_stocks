package com.bintoufha.gestionStocks.services.strategieVente;

import com.bintoufha.gestionStocks.model.LotStocks;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class LifoStrategie implements CoutStrategie{
    @Override
    public BigDecimal calculateCost(List<LotStocks> lots, BigDecimal quantity) throws Exception {
        BigDecimal remaining = quantity;
        BigDecimal totalValue = BigDecimal.ZERO;

        // Inverser pour LIFO
        Collections.reverse(lots);

        for (LotStocks lot : lots) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal take = lot.getQteRestant().min(remaining);
            totalValue = totalValue.add(take.multiply(lot.getPrixUnitaire()));

            remaining = remaining.subtract(take);
        }

        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            throw new Exception("Stock insuffisant pour LIFO");
        }

        return totalValue;
    }
}
