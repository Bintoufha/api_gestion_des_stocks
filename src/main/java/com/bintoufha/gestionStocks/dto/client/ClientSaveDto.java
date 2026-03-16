package com.bintoufha.gestionStocks.dto.client;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ClientSaveDto {
    private UUID uuid;
    private String nomPrenomClient;
    private String emailClient;
    private String telephoneClient;
    private AddresseDataDto addresse;
    private UUID idEntreprise;
}
