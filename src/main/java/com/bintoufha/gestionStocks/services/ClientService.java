package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.client.ClientListDto;
import com.bintoufha.gestionStocks.dto.client.ClientSaveDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ClientSaveDto save (ClientSaveDto clientsDto);

    ClientListDto findByUuid(UUID uuid);

    List<ClientListDto> findAll();

    void deleteByUuid(UUID uuid);
}
