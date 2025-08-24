package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Addresse;

import com.bintoufha.gestionStocks.model.Clients;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder

public class ClientsDto {

    private UUID uuid;

    private String nomPrenomClient;

    private String emailClient;

    private String telephoneClient;

    private AddresseDto addresse;

    private UUID idEntreprise;

    private String photoClient;
    //@JsonIgnore
    private List<CommandeClientsDto> commandeClient;

    public static Clients toEntity(ClientsDto clientsDto){
        if (clientsDto == null){
            return null;
        }

        Clients clients = new Clients();
        clients.setUuid(clientsDto.getUuid());
        clients.setNomPrenomClient(clientsDto.getNomPrenomClient());
        clients.setEmailClient(clientsDto.getEmailClient());
        clients.setTelephoneClient(clientsDto.getTelephoneClient());
        clients.setIdEntreprise(clientsDto.getIdEntreprise());
        //clients.setAddresse(clientsDto.toEntity(clientsDto.getAddresse()));
        clients.setPhotoClient(clientsDto.getPhotoClient());
        return clients;

    }
    public static ClientsDto fromEntity(Clients clients){
        if (clients == null){
            return null;
        }
        return  ClientsDto.builder()
                .uuid(clients.getUuid())
                .nomPrenomClient(clients.getNomPrenomClient())
                .emailClient(clients.getEmailClient())
                .telephoneClient(clients.getTelephoneClient())
                .photoClient(clients.getPhotoClient())
                .idEntreprise(clients.getIdEntreprise())
                .build();
    }
}
