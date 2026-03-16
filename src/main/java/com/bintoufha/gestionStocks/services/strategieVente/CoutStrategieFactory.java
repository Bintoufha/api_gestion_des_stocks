package com.bintoufha.gestionStocks.services.strategieVente;

public class CoutStrategieFactory {

    public static CoutStrategie getStrategy(String method) {

        switch (method.toUpperCase()) {
            case "FIFO":
                return new FifoStrategie();
            case "LIFO":
                return new LifoStrategie();
            case "PMP":
                return new PmpStrategie();
            default:
                throw new IllegalArgumentException("Méthode de costing invalide : " + method);
        }
    }
}
