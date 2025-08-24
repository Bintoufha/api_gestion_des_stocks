package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.LigneCommandeFournisseurs;
import com.bintoufha.gestionStocks.model.Ventes;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class VentesDto {

    private UUID uuid;
    private String reference;

    private String typeVente;

    private UUID idEntreprise;

    private BigDecimal montantPayer;

    private BigDecimal montantReste;

    private BigDecimal montantTotal;

    public static VentesDto fromEntity(Ventes ventes) {
        if (ventes == null) {
            return null;
        }
        return VentesDto.builder()
                .uuid(ventes.getUuid())
                .reference(ventes.getReference())
                .typeVente(ventes.getTypeVente())
                .idEntreprise(ventes.getIdEntreprise())
                .montantPayer(ventes.getMontantPayer())
                .montantReste(ventes.getMontantReste())
                .montantTotal(ventes.getMontantTotal())
                .build();
    }

    public static Ventes toEntity(VentesDto ventesDto) {
        if (ventesDto == null) {
            return null;
        }
        Ventes ventes = new Ventes();
        ventes.setUuid(ventesDto.getUuid());
        ventes.setReference(ventesDto.getReference());
        ventes.setTypeVente(ventesDto.getTypeVente());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());
        ventes.setMontantPayer(ventesDto.getMontantPayer());
        ventes.setMontantReste(ventesDto.getMontantReste());
        ventes.setMontantTotal(ventesDto.getMontantTotal());
        return ventes;
    }

}
