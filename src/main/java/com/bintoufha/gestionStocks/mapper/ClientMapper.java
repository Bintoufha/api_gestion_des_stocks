package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.dto.client.ClientDetailDto;
import com.bintoufha.gestionStocks.dto.client.ClientListDto;
import com.bintoufha.gestionStocks.dto.client.ClientSaveDto;
import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.Clients;

public class ClientMapper {

    public static Clients toEntity(ClientSaveDto dto) {
        Clients client = new Clients();
        client.setNomPrenomClient(dto.getNomPrenomClient());
        client.setEmailClient(dto.getEmailClient());
        client.setTelephoneClient(dto.getTelephoneClient());
        client.setIdEntreprise(dto.getIdEntreprise());
        if (dto.getAddresse() != null) {
            Addresse addresse = new Addresse();
            addresse.setAddresse1(dto.getAddresse().getAddresse1());
            addresse.setVille(dto.getAddresse().getVille());
            addresse.setPays(dto.getAddresse().getPays());
            addresse.setCodePostale(dto.getAddresse().getCodePostale());
        }

        return client;
    }

    public static ClientListDto toListDto(Clients client) {
        return ClientListDto.builder()
                .uuid(client.getUuid())
                .nomPrenomClient(client.getNomPrenomClient())
                .emailClient(client.getEmailClient())
                .telephoneClient(client.getTelephoneClient())
                .build();
    }

    public static ClientDetailDto toDetailDto(Clients client) {
        return ClientDetailDto.builder()
                .uuid(client.getUuid())
                .nomPrenomClient(client.getNomPrenomClient())
                .emailClient(client.getEmailClient())
                .telephoneClient(client.getTelephoneClient())
                .photoClient(client.getPhotoClient())
                .idEntreprise(client.getIdEntreprise())
                .addresse(
                        AddresseDataDto.builder()
                                .Addresse1(client.getAddresse().getAddresse1())
                                .Addresse2(client.getAddresse().getAddresse2())
                                .Pays(client.getAddresse().getPays())
                                .Ville(client.getAddresse().getVille())
                                .CodePostale(client.getAddresse().getCodePostale())
                                .build()
                )
                .build();
    }

    // Entity → DTO (pour save / update)
    public static ClientSaveDto toSaveDto(Clients clientSaveDto) {
        if (clientSaveDto == null) return null;

        return ClientSaveDto.builder()
                .nomPrenomClient(clientSaveDto.getNomPrenomClient())
                .emailClient(clientSaveDto.getEmailClient())
                .telephoneClient(clientSaveDto.getTelephoneClient())
                .idEntreprise(clientSaveDto.getIdEntreprise())
                .addresse(
                        AddresseDataDto.builder()
                                .Ville(clientSaveDto.getAddresse().getVille())
                                .Pays(clientSaveDto.getAddresse().getPays())
                                .CodePostale(clientSaveDto.getAddresse().getCodePostale())
                                .Addresse1(clientSaveDto.getAddresse().getAddresse1())
                                .Addresse2(clientSaveDto.getAddresse().getAddresse2())
                                .build()
                )
                .build();
    }
}
