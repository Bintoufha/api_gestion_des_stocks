package com.bintoufha.gestionStocks.dto;

import com.bintoufha.gestionStocks.model.Addresse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddresseDto {
    private String Addresse1;

    private String Addresse2;

    private String Ville;

    private String CodePostale;

    private String Pays;

    public static AddresseDto fromEntity (Addresse addresse){
        if (addresse == null){
            return  null;
        }
        return AddresseDto.builder()
                .Addresse1(addresse.getAddresse1())
                .Addresse2(addresse.getAddresse2())
                .CodePostale(addresse.getCodePostale())
                .Ville(addresse.getVille())
                .Pays(addresse.getPays())
                .build();
    }
    public static Addresse toEntity (AddresseDto addresseDto){
        if (addresseDto == null){
            return  null;
        }
        Addresse addresse = new Addresse();
        addresse.setAddresse1(addresseDto.getAddresse1());
        addresse.setAddresse2(addresseDto.getAddresse2());
        addresse.setCodePostale(addresseDto.getCodePostale());
        addresse.setVille(addresseDto.getVille());
        addresse.setPays(addresseDto.getPays());
        return addresse;
    }
}
