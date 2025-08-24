package com.bintoufha.gestionStocks.services;

import com.bintoufha.gestionStocks.dto.RolesDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RolesDto save (RolesDto rolesDto);

    RolesDto findByUuid(UUID uuid);

    List<RolesDto> findAll();

    void deleteByUuid(UUID uuid);
}
