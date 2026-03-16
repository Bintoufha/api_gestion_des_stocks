package com.bintoufha.gestionStocks.dto.utilisateur;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class UtilisateurByEmailDto {

    private EntrepriseSaveDto entreprise;

    private String nomPrenomUtilisateurs;


    private UUID uuid;

    private String email;

    private String pwd;

    private boolean actif;

    private List<RoleSaveDto> roles;




}
