package com.bintoufha.gestionStocks.dto.client;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ClientListDto {
    private UUID uuid;
    private String nomPrenomClient;
    private String telephoneClient;
    private String emailClient;
}
