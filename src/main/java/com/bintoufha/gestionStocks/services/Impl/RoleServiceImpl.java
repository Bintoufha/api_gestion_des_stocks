package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.RolesDto;
import com.bintoufha.gestionStocks.repository.RoleRepository;
import com.bintoufha.gestionStocks.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final DefaultErrorAttributes errorAttributes;
    private RoleRepository roleRepository;

    public RoleServiceImpl(DefaultErrorAttributes errorAttributes, RoleRepository roleRepository) {
        this.errorAttributes = errorAttributes;
        this.roleRepository = roleRepository;
    }

    @Override
    public RolesDto save(RolesDto rolesDto) {
        return null;
    }

    @Override
    public RolesDto findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public List<RolesDto> findAll() {
        return List.of();
    }

    @Override
    public void deleteByUuid(UUID uuid) {

    }
}
