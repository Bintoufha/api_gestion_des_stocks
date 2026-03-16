package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.vente.VentesDto;
import com.bintoufha.gestionStocks.model.Ventes;

public class VenteMapper {

    public static VentesDto fromEntity(Ventes ventes) {
        if (ventes == null) {
            return null;
        }
        return VentesDto.builder()
                .uuid(ventes.getUuid())
                .reference(ventes.getReference())
                .valorisationMethod(ventes.getValorisationMethod())
                .idEntreprise(ventes.getIdEntreprise())
                .montantPayer(ventes.getMontantPayer())
                .montantReste(ventes.getMontantReste())
                .montantTotal(ventes.getMontantTotal())
                .dateCommande(ventes.getDateCommande())
                .build();
    }

    public static Ventes toEntity(VentesDto ventesDto) {
        if (ventesDto == null) {
            return null;
        }
        Ventes ventes = new Ventes();
        ventes.setUuid(ventesDto.getUuid());
        ventes.setReference(ventesDto.getReference());
        ventes.setValorisationMethod(ventesDto.getValorisationMethod());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());
        ventes.setMontantPayer(ventesDto.getMontantPayer());
        ventes.setMontantReste(ventesDto.getMontantReste());
        ventes.setMontantTotal(ventesDto.getMontantTotal());
        ventes.setDateCommande(ventesDto.getDateCommande());

        return ventes;
    }
}
