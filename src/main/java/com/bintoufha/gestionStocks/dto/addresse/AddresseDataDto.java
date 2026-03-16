package com.bintoufha.gestionStocks.dto.addresse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddresseDataDto {
    private String Addresse1;

    private String Addresse2;

    private String Ville;

    private String CodePostale;

    private String Pays;

}
