package com.bintoufha.gestionStocks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChangerMotDePasseUtilisateurDto {

    private UUID uuid;

    private String pwd;

    private  String pwdConfirmer;
}
