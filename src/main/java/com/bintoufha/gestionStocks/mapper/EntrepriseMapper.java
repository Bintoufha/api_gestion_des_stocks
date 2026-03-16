package com.bintoufha.gestionStocks.mapper;

import com.bintoufha.gestionStocks.dto.addresse.AddresseDataDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseConfigSaveDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.EntrepriseConfig;
import com.bintoufha.gestionStocks.model.Entreprises;

import java.util.List;

public class EntrepriseMapper {

    public static EntrepriseSaveDto fromEntity(Entreprises entreprises) {
        if (entreprises == null) {
            return null;
        }

        return EntrepriseSaveDto.builder()
                .uuid(entreprises.getUuid())
                .nomEntreprise(entreprises.getNomEntreprise())
                .numero(entreprises.getNumero())
                .photoEntreprise(entreprises.getPhotoEntreprise())
                .siteWebUrl(entreprises.getSiteWebUrl())
                //.addresse(AddresseDto.fromEntity(entreprises.getAddresse()))
                .email(entreprises.getEmail())
                .codeFiscale(entreprises.getCodeFiscale())
                .description(entreprises.getDescription())
                .addresse(
                        AddresseDataDto.builder()
                                .Ville(entreprises.getAddresse().getVille())
                                .Pays(entreprises.getAddresse().getPays())
                                .CodePostale(entreprises.getAddresse().getCodePostale())
                                .Addresse1(entreprises.getAddresse().getAddresse1())
                                .Addresse2(entreprises.getAddresse().getAddresse2())
                                .build()
                )
                .build();
    }

    public static Entreprises toEntity(EntrepriseSaveDto entrepriseDto) {
        if (entrepriseDto == null) {
            return null;
        }
        Entreprises entreprises = new Entreprises();
        entreprises.setUuid(entrepriseDto.getUuid());
        entreprises.setNomEntreprise(entrepriseDto.getNomEntreprise());
        //entreprises.setAddresse(AddresseDto.toEntity(entrepriseDto.getAddresse()));
        entreprises.setEmail(entrepriseDto.getEmail());
        entreprises.setPhotoEntreprise(entrepriseDto.getPhotoEntreprise());
        entreprises.setCodeFiscale(entrepriseDto.getCodeFiscale());
        entreprises.setDescription(entrepriseDto.getDescription());
        entreprises.setNumero(entrepriseDto.getNumero());
        entreprises.setSiteWebUrl(entrepriseDto.getSiteWebUrl());
        if (entrepriseDto.getAddresse() != null) {
            Addresse addresse = new Addresse();
            addresse.setAddresse1(entrepriseDto.getAddresse().getAddresse1());
            addresse.setVille(entrepriseDto.getAddresse().getVille());
            addresse.setPays(entrepriseDto.getAddresse().getPays());
            addresse.setCodePostale(entrepriseDto.getAddresse().getCodePostale());
        }
        return entreprises;

    }

    public static EntrepriseListDto toListDto(Entreprises entreprises) {
        if (entreprises == null) return null;

        return EntrepriseListDto.builder()
                .nomEntreprise(entreprises.getNomEntreprise())
                .uuid(entreprises.getUuid())
                .email(entreprises.getEmail())
                .numero(entreprises.getNumero())
                .description(entreprises.getDescription())
                .codeFiscale(entreprises.getCodeFiscale())
                .build();
    }

    public static EntrepriseConfigSaveDto fromEntityEntrepriseConfig(EntrepriseConfig entrepriseConfig) {
        if (entrepriseConfig == null) {
            return null;
        }
        return EntrepriseConfigSaveDto.builder()
                .entreprise(entrepriseConfig.getEntreprise())
                .margeDetail(entrepriseConfig.getMargeDetail())
                .margeGros(entrepriseConfig.getMargeGros())
                .categorie(entrepriseConfig.getCategorie())
                .configurePar(entrepriseConfig.getConfigurePar())
                .build();
    }

    public static EntrepriseConfig toEntityEntrepriseConfig(EntrepriseConfigListDto entrepriseConfigDto) {
        if (entrepriseConfigDto == null) {
            return null;
        }
        EntrepriseConfig configEntreprise = new EntrepriseConfig();
        configEntreprise.setEntreprise(entrepriseConfigDto.getEntreprise());
        configEntreprise.setCategorie(entrepriseConfigDto.getCategorie());
        configEntreprise.setMargeDetail(entrepriseConfigDto.getMargeDetail());
        configEntreprise.setMargeGros(entrepriseConfigDto.getMargeGros());
        configEntreprise.setConfigurePar(entrepriseConfigDto.getConfigurePar());
        configEntreprise.calculerPrixDetail(entrepriseConfigDto.getCalculerPrixDetail());
        configEntreprise.calculerPrixGros(entrepriseConfigDto.getCalculerPrixGros());

        return configEntreprise;
    }

    public static EntrepriseConfigListDto toListDtoEntrepriseConfig(EntrepriseConfig entreprises) {
        if (entreprises == null) return null;

        return EntrepriseConfigListDto.builder()
                .uuid(entreprises.getUuid())
                .build();
    }

    // Conversion d'une liste d'entités
    public List<EntrepriseConfigListDto> toLisEntrepriseConfigDto(List<EntrepriseConfig> entities) {
        return null;
    }

}
