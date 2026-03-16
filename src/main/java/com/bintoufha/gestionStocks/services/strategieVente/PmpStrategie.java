package com.bintoufha.gestionStocks.services.strategieVente;

import com.bintoufha.gestionStocks.model.LotStocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PmpStrategie implements CoutStrategie{
    @Override
    public BigDecimal calculateCost(List<LotStocks> lots, BigDecimal quantity) throws Exception {
        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalValue = BigDecimal.ZERO;

        for (LotStocks lot : lots) {
            totalQty = totalQty.add(lot.getQteRestant());
            totalValue = totalValue.add(lot.getQteRestant().multiply(lot.getPrixUnitaire()));
        }

        if (totalQty.compareTo(quantity) < 0) {
            throw new Exception("Stock insuffisant pour PMP");
        }

        BigDecimal pmp = totalValue.divide(totalQty, 6, RoundingMode.HALF_UP);

        return pmp.multiply(quantity);
    }
}
