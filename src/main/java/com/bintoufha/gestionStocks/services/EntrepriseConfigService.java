package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigSaveDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface EntrepriseConfigService {

    EntrepriseConfigSaveDto save(EntrepriseConfigSaveDto configDto);

    EntrepriseConfigListDto findByUuid(UUID uuid);
    ;

    List<EntrepriseConfigListDto> findAll();


    void desactiverTarification(UUID uuid);

    void activerTarification(UUID uuid);


    List<EntrepriseConfigListDto> getTarificationsByBoutique(UUID entrepriseUuid);

    List<EntrepriseConfigListDto> getTarificationsActivesByBoutique(UUID entrepriseUuid);

    EntrepriseConfigListDto getTarificationByBoutiqueAndCategorie(UUID entrepriseUuid, UUID categorieUuid);

    BigDecimal calculerPrix(UUID entrepriseUuid, UUID categorieUuid, BigDecimal prixAchat,String typePrix);



}
