package com.bintoufha.gestionStocks.utils;

public interface Constante {

    String APP_ROOT = "/gestiondesstocks/v1";

    String VENTE_ENDPOINT = APP_ROOT + "/vente";
    String CREATE_VENTE_ENDPOINT = VENTE_ENDPOINT + "/create_vente";
    String FIND_VENTE_ENDPOINT_BY_UUID = VENTE_ENDPOINT + "/recherche/uuidVente";
    String FIND_VENTE_ENDPOINT_BY_REFERENCE= VENTE_ENDPOINT + "/refernce";
    String FIND_ALL_VENTE_ENDPOINT= VENTE_ENDPOINT + "/AllVente";
    String DELETE_VENTE_ENDPOINT_BY_UUID = VENTE_ENDPOINT + "/uuidVente";

}
