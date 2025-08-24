package com.bintoufha.gestionStocks.services;


import com.bintoufha.gestionStocks.dto.CategoriesDto;

import java.util.List;
import java.util.UUID;

public interface CategorieService {

    CategoriesDto save (CategoriesDto dto);

    CategoriesDto findByUuid(UUID uuid);

    CategoriesDto findByCode (String code);

    List<CategoriesDto> findAll();

    void delete(UUID uuid);
}
