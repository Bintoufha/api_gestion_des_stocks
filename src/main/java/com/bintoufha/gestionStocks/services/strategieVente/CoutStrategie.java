package com.bintoufha.gestionStocks.services.strategieVente;

import com.bintoufha.gestionStocks.model.LotStocks;

import java.math.BigDecimal;
import java.util.List;

public interface CoutStrategie {
    BigDecimal calculateCost(List<LotStocks> lots, BigDecimal quantity) throws Exception;
}
