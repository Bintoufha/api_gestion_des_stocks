package com.bintoufha.gestionStocks.controllers.api;

import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.UUID;

import static com.bintoufha.gestionStocks.utils.Constante.*;


@Tag(name = "Vente", description = "API de gestion des vente")

public interface VentesApi {

    @PostMapping(
        value=CREATE_VENTE_ENDPOINT,
    consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<VentesDto> save(@RequestBody VentesDto ventesDto);

    @GetMapping(
        value=FIND_VENTE_ENDPOINT_BY_UUID,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<VentesDto> findByUuid(@PathVariable("uuidVente") UUID uuid);

    @GetMapping(
        value=FIND_VENTE_ENDPOINT_BY_REFERENCE,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<VentesDto> findByReference(@PathVariable String reference);

    @GetMapping(
        value=FIND_ALL_VENTE_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<List<VentesDto>> findAll();

    @DeleteMapping(
        value=DELETE_VENTE_ENDPOINT_BY_UUID,
            produces = MediaType.APPLICATION_JSON_VALUE  // ✅ Important !
        )
    ResponseEntity<VentesDto> deleteByUuid(@PathVariable("uuidVente") UUID uuid);
}
