package com.bintoufha.gestionStocks.dto.fournisseurs;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FournisseurSaveDto {
    private String nomPrenomFournisseurs;
    private String telephoneFournisseurs;
    private String emailFournisseurs;
    private AddresseDataDto addresse;
    private UUID idEntreprise;
}
