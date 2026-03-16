package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.StatutEntreprise;


import java.util.List;
import java.util.UUID;


public interface EntrepriseService {
    EntrepriseSaveDto save (EntrepriseSaveDto entrepriseDto);

    EntrepriseListDto findByUuid(UUID uuid);

    List<EntrepriseListDto> findAll();

    List<EntrepriseListDto> getEntrepriseByTypeEntreprises(UUID typeUuid) ;

    List<EntrepriseListDto> getEntrepriseByVille(String ville) ;

    List<EntrepriseListDto> getEntrepriseByStatut(StatutEntreprise statut);

    void deleteByUuid(UUID uuid);
}
