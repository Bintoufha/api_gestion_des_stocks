package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.RolesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

@Tag(name = "Role", description = "API de gestion des roles")
//@RestController
//@RequestMapping(APP_ROOT + "/roles")
public interface RolesApi {


    @PostMapping(APP_ROOT + "/roles/create_role")
    ResponseEntity<RolesDto> save(@RequestBody  RolesDto rolesDto);

    @GetMapping(APP_ROOT + "/roles/recherche/{uuidRole}")
    ResponseEntity<RolesDto> findByUuid(@PathVariable("uuidRole") UUID uuid);

    @GetMapping(APP_ROOT + "/roles/All_Role")
    ResponseEntity<List<RolesDto>> findAll();

    @DeleteMapping(APP_ROOT + "/roles/{uuidRole}")
    ResponseEntity<RolesDto> deleteByUuid(@PathVariable("uuidRole") UUID uuid);
}
