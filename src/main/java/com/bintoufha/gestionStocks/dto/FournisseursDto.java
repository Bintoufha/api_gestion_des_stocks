package com.bintoufha.gestionStocks.dto;


import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.CommandeFournisseurs;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.Fournisseurs;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FournisseursDto {

    private UUID uuid;

    private String nomPrenomFournisseurs;

    private String emailFournisseurs;

    private String telephoneFournisseurs;

    private Addresse addresse;

    private UUID idEntreprise;

    private String photoFournisseurs;

    private List<CommandeFournisseursDto> CommandeFournisseurs;

    public static FournisseursDto fromEntity(Fournisseurs fournisseurs){
        if (fournisseurs == null){
            return null;
        }

        return FournisseursDto.builder()
                .uuid(fournisseurs.getUuid())
                .nomPrenomFournisseurs(fournisseurs.getNomPrenomFournisseurs())
                .emailFournisseurs(fournisseurs.getEmailFournisseurs())
                .telephoneFournisseurs(fournisseurs.getTelephoneFournisseurs())
                .idEntreprise(fournisseurs.getIdEntreprise())
                .photoFournisseurs(fournisseurs.getPhotoFournisseurs())
                .addresse(fournisseurs.getAddresse())
                .build();
    }

    public static Fournisseurs toEntity(FournisseursDto fournisseursDto){
        if (fournisseursDto == null){
            return null;
        }
        Fournisseurs fournisseurs = new Fournisseurs();
        fournisseurs.setUuid(fournisseursDto.getUuid());
        fournisseurs.setNomPrenomFournisseurs(fournisseursDto.getNomPrenomFournisseurs());
        fournisseurs.setAddresse(fournisseursDto.getAddresse());
        fournisseurs.setEmailFournisseurs(fournisseursDto.getEmailFournisseurs());
        fournisseurs.setIdEntreprise(fournisseursDto.getIdEntreprise());
        fournisseurs.setTelephoneFournisseurs(fournisseursDto.getTelephoneFournisseurs());
        fournisseurs.setPhotoFournisseurs(fournisseursDto.getPhotoFournisseurs());
        return fournisseurs;

    }
}
