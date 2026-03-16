package com.bintoufha.gestionStocks.services.strategieVente;

import com.bintoufha.gestionStocks.model.LotStocks;

import java.math.BigDecimal;
import java.util.List;

public class FifoStrategie implements CoutStrategie{
    @Override
    public BigDecimal calculateCost(List<LotStocks> lots, BigDecimal quantite) throws Exception {
        BigDecimal restant = quantite;
        BigDecimal totalValue = BigDecimal.ZERO;

        for (LotStocks lot : lots) {
            if (restant.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal take = lot.getQteRestant().min(restant);
            totalValue = totalValue.add(take.multiply(lot.getPrixUnitaire()));

            restant = restant.subtract(take);
        }

        if (restant.compareTo(BigDecimal.ZERO) > 0) {
            throw new Exception("Stock insuffisant pour FIFO");
        }

        return totalValue;
    }
}
