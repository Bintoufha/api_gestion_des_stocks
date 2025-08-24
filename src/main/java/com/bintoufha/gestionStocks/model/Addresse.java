package com.bintoufha.gestionStocks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Addresse {

    @Column(name = "addresse1")
    private String Addresse1;

    @Column(name = "addresse2")
    private String Addresse2;

    @Column(name = "ville")
    private String Ville;

    @Column(name = "codepostale")
    private String CodePostale;

    @Column(name = "pays")
    private String Pays;
}
