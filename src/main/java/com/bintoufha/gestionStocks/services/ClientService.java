package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.ClientsDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ClientsDto save (ClientsDto clientsDto);

    ClientsDto findByUuid(UUID uuid);

    List<ClientsDto> findAll();

    void deleteByUuid(UUID uuid);
}
