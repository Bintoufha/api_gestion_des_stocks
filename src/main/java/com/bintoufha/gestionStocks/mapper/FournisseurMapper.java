package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurListDto;
import com.bintoufha.gestionStocks.dto.fournisseurs.FournisseurSaveDto;
import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.Fournisseurs;

public class FournisseurMapper {

    public static Fournisseurs toEntity(FournisseurSaveDto dto) {
        Fournisseurs f = new Fournisseurs();
        f.setNomPrenomFournisseurs(dto.getNomPrenomFournisseurs());
        f.setTelephoneFournisseurs(dto.getTelephoneFournisseurs());
        f.setEmailFournisseurs(dto.getEmailFournisseurs());
        f.setIdEntreprise(dto.getIdEntreprise());
        if (dto.getAddresse() != null) {
            Addresse addresse = new Addresse();
            addresse.setAddresse1(dto.getAddresse().getAddresse1());
            addresse.setVille(dto.getAddresse().getVille());
            addresse.setPays(dto.getAddresse().getPays());
            addresse.setCodePostale(dto.getAddresse().getCodePostale());
        }
        return f;
    }

    public static FournisseurListDto toListDto(Fournisseurs f) {
        return FournisseurListDto.builder()
                .uuid(f.getUuid())
                .nomPrenomFournisseurs(f.getNomPrenomFournisseurs())
                .telephoneFournisseurs(f.getTelephoneFournisseurs())
                .build();
    }

    // Entity → DTO (pour save / update)
    public static FournisseurSaveDto toSaveDto(Fournisseurs fournisseurs) {
        if (fournisseurs == null) return null;

        return FournisseurSaveDto.builder()
                .nomPrenomFournisseurs(fournisseurs.getNomPrenomFournisseurs())
                .emailFournisseurs(fournisseurs.getEmailFournisseurs())
                .telephoneFournisseurs(fournisseurs.getTelephoneFournisseurs())
                .idEntreprise(fournisseurs.getIdEntreprise())
                .addresse(
                        AddresseDataDto.builder()
                                .Ville(fournisseurs.getAddresse().getVille())
                                .Pays(fournisseurs.getAddresse().getPays())
                                .CodePostale(fournisseurs.getAddresse().getCodePostale())
                                .Addresse1(fournisseurs.getAddresse().getAddresse1())
                                .Addresse2(fournisseurs.getAddresse().getAddresse2())
                                .build()
                )
                .build();
    }
}
