package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.VentesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.*;


@Tag(name = "Vente", description = "API de gestion des vente")

public interface VentesApi {

    @PostMapping(CREATE_VENTE_ENDPOINT)
    ResponseEntity<VentesDto> save(@RequestBody VentesDto ventesDto);

    @GetMapping(FIND_VENTE_ENDPOINT_BY_UUID)
    ResponseEntity<VentesDto> findByUuid(@PathVariable("uuidVente") UUID uuid);

    @GetMapping(FIND_VENTE_ENDPOINT_BY_REFERENCE)
    ResponseEntity<VentesDto> findByReference(@PathVariable String reference);

    @GetMapping(FIND_ALL_VENTE_ENDPOINT)
    ResponseEntity<VentesDto> findAll();

    @DeleteMapping(DELETE_VENTE_ENDPOINT_BY_UUID)
    ResponseEntity<VentesDto> deleteByUuid(@PathVariable("uuidVente") UUID uuid);
}
