package com.bintoufha.gestionStocks.services;


import com.bintoufha.gestionStocks.dto.categorie.CategorieListDto;
import com.bintoufha.gestionStocks.dto.categorie.CategorieSaveDto;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    CategorieSaveDto save(CategorieSaveDto dto);

    CategorieListDto findByUuid(UUID uuid);

    CategorieListDto findByCode (String code);

    List<CategorieListDto> findAll();

    void delete(UUID uuid);
}
