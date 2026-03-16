package com.bintoufha.gestionStocks.model;

public enum EtatCommande {

/********************************pour les clients *****************************************/

    BROUILLON , // Brouillon (Draft / Créée / Initiée)

    EN_ATTENTE_VALIDATION,  //En attente de validation (Pending / À valider)

    VALIDEE , // Validée (Confirmed / Acceptée)

    EN_PREPARATION, // En préparation (Processing / En cours de traitement)

    EXPEDIEE, // Expédiée (Shipped / Sortie de stock)

    LIVREE,  // Livrée (Delivered)

    PARCIELLEMENT_LIVREE, //Partiellement livrée (Partially Delivered)

    ANNULLE, // Annulée (Cancelled)

    RETOUR, // Retour / Remboursée (Returned / Refunded)

    INCONNU,

/********************************pour les fournisseurs*****************************************/

    CREEE, //Créée (brouillon)

    ENVOYEE, //Envoyée au fournisseur

    EN_ATTENTE_RECEPTION, //En attente de réception

    PARCIELLEMENT_RECU, //Partiellement reçue

    RECU, //Reçue (stock augmenté)

}
