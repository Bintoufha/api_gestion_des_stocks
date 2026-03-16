package com.bintoufha.gestionStocks.model;

public enum RoleScope {

    GLOBAL,      // Accès à toutes les boutiques (SuperAdmin, AdminGeneral)
    ENTREPRISES,    // Accès limité à une boutique spécifique
    REGIONAL     // Accès à plusieurs boutiques d'une région
}
