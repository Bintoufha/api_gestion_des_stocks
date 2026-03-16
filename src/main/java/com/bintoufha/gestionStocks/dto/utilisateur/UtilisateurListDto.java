package com.bintoufha.gestionStocks.dto.utilisateur;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Builder
@Data
public class UtilisateurListDto {

    private UUID uuid;

    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    private String email;

    private String telephoneUtilisateurs;

    private AddresseDataDto addresse;

    private EntrepriseSaveDto entreprise;


    private List<RoleSaveDto> roles;
}
