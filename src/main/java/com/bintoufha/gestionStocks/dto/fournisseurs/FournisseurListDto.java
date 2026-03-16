package com.bintoufha.gestionStocks.dto.fournisseurs;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FournisseurListDto {
    private UUID uuid;
    private String nomPrenomFournisseurs;
    private String telephoneFournisseurs;
    private String emailFournisseurs;

}
