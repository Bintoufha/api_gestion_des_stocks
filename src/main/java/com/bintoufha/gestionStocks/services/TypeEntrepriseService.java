package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseListDto;
import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.TypeEntreprises;

import java.util.List;
import java.util.UUID;

public interface TypeEntrepriseService {

    TypeEntrepriseSaveDto save(UUID uuid, TypeEntrepriseSaveDto typeEntrepriseSaveDto);

    TypeEntrepriseListDto findByUuid(UUID uuid);

    List<TypeEntreprises> getAllTypeEntreprise();

    TypeEntreprises getTypeEntrepiseByNomTypeEntreprise(String nom);

    void deleteTypeBoutique(UUID uuid);
}
