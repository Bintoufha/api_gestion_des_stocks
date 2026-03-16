package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseListDto;
import com.bintoufha.gestionStocks.dto.typeEntreptise.TypeEntrepriseSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.mapper.ArticleMapper;
import com.bintoufha.gestionStocks.mapper.TypeEntrepriseMapper;
import com.bintoufha.gestionStocks.model.TypeEntreprises;
import com.bintoufha.gestionStocks.repository.TypeEntrepriseRepository;
import com.bintoufha.gestionStocks.services.TypeEntrepriseService;
import com.bintoufha.gestionStocks.validator.TypeEntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class TypeEntrepriseServiceImpl implements TypeEntrepriseService {

    @Autowired
    private TypeEntrepriseRepository typeEntrepriseRepository;

    @Override
    public TypeEntrepriseSaveDto save(UUID uuid, TypeEntrepriseSaveDto typeEntrepriseSaveDto) {
        // 1️⃣
        List<String> errors = TypeEntrepriseValidator.validate(typeEntrepriseSaveDto);
        if (!errors.isEmpty()) {
            throw new InvalEntityException(
                    "Type de boutique invalide", ErrorCodes.TYPE_BOUTIQUE_NOT_VALID, errors
            );
        }
        TypeEntreprises typeEntreprises;
        if (uuid == null) {
            // Création
            if (typeEntrepriseRepository.existsByNomTypeEntreprise(typeEntrepriseSaveDto.getNomTypeEntreprise())) {
                throw new RuntimeException("Un type de boutique avec ce nom existe déjà");
            }
            if (typeEntrepriseSaveDto.getCode() != null && typeEntrepriseRepository.existsByCode(typeEntrepriseSaveDto.getCode())) {
                throw new RuntimeException("Un type de boutique avec ce code existe déjà");
            }
            typeEntreprises = TypeEntrepriseMapper.toEntity(typeEntrepriseSaveDto);
        } else {
            // Mise à jour
            typeEntreprises = typeEntrepriseRepository.findByUuid(uuid)
                    .orElseThrow(() -> new RuntimeException("Type de boutique non trouvé"));
            if (!typeEntreprises.getNomTypeEntreprise().equals(typeEntrepriseSaveDto.getNomTypeEntreprise())
                    && typeEntrepriseRepository.existsByNomTypeEntreprise(typeEntrepriseSaveDto.getNomTypeEntreprise())) {
                throw new RuntimeException("Un type de boutique avec ce nom existe déjà");
            }
            if (typeEntrepriseSaveDto.getCode() != null &&
                    !typeEntrepriseSaveDto.getCode().equals(typeEntreprises.getCode()) &&
                    typeEntrepriseRepository.existsByCode(typeEntrepriseSaveDto.getCode())) {
                throw new RuntimeException("Un type de boutique avec ce code existe déjà");
            }
            // Mise à jour des champs
            typeEntreprises.setNomTypeEntreprise(typeEntrepriseSaveDto.getNomTypeEntreprise());
            typeEntreprises.setDescription(typeEntrepriseSaveDto.getDescription());
            typeEntreprises.setCode(typeEntrepriseSaveDto.getCode());
        }
        // Sauvegarde
        TypeEntreprises saved = typeEntrepriseRepository.save(typeEntreprises);
        // Retourner DTO propre
        return TypeEntrepriseMapper.fromEntity(saved);

    }

    @Override
    public TypeEntrepriseListDto findByUuid(UUID uuid) {

        if (uuid == null) {
            log.error("Identifiant article est introuvable");
            throw new IllegalArgumentException("UUID de l'article ne peut pas être null");
        }

        // Récupération de l'article ou exception si non trouvé
        TypeEntreprises typeEntreprises = typeEntrepriseRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun Type de boutique avec uuid " + uuid + " n'a été trouvé",
                        ErrorCodes.CONFIG_NOT_FOUND
                ));

        // Transformation en DTO pour exposer uniquement les données nécessaires
        return TypeEntrepriseMapper.toListDto(typeEntreprises);
    }

    @Override
    public List<TypeEntreprises> getAllTypeEntreprise() {
        return typeEntrepriseRepository.findAll();
    }

    @Override
    public TypeEntreprises getTypeEntrepiseByNomTypeEntreprise(String nom) {
        return typeEntrepriseRepository.findByNomTypeEntreprise(nom)
                .orElseThrow(() -> new RuntimeException("Type de boutique non trouvé"));
    }

    @Override
    public void deleteTypeBoutique(UUID uuid) {
        TypeEntreprises typeEntreprises = typeEntrepriseRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Type de boutique non trouvé"));

        // Vérifier si utilisé par des boutiques
        if (!typeEntreprises.getEntreprises().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : ce type est utilisé par des boutiques");
        }
       typeEntrepriseRepository.delete(typeEntreprises);
    }
}
