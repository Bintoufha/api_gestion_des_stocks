package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurListDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;

import java.util.List;
import java.util.UUID;



public interface FournisseurService {
    FournisseurSaveDto save (FournisseurSaveDto fournisseursDto);

    FournisseurListDto findByUuid(UUID uuid);

    List<FournisseurListDto> findAll();

    void deleteByUuid(UUID uuid);
}
