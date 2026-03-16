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
public class UtilisateurSaveDto {

    private UUID uuid;

    private String nomPrenomUtilisateurs;

    private Instant dateNaissance;

    private String email;

    private String pwd;

    private String telephoneUtilisateurs;

    private AddresseDataDto addresse;

    private EntrepriseSaveDto entreprise;

    private String photoUtilisateurs;

    private List<RoleSaveDto> roles;
}
