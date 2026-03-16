package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseListDto;
import com.bintoufha.gestionStocks.dto.entreprise.EntrepriseSaveDto;
import com.bintoufha.gestionStocks.dto.role.RoleSaveDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.mapper.EntrepriseMapper;
import com.bintoufha.gestionStocks.mapper.RoleMapper;
import com.bintoufha.gestionStocks.mapper.TypeEntrepriseMapper;
import com.bintoufha.gestionStocks.model.Addresse;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.model.StatutEntreprise;
import com.bintoufha.gestionStocks.model.TypeEntreprises;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.repository.RoleRepository;
import com.bintoufha.gestionStocks.repository.TypeEntrepriseRepository;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.EntrepriseService;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import com.bintoufha.gestionStocks.validator.EntreprisesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private final UtilisateurService utilisateurService;
    private final UtilisateursRepository utilisateurRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final RoleRepository roleRepository;
    private final TypeEntrepriseRepository typeEntrepriseRepository;


    public EntrepriseServiceImpl(
            EntrepriseRepository entrepriseRepository,
            UtilisateurService utilisateurService,
            UtilisateursRepository utilisateurRepository,
            RoleRepository roleRepository, TypeEntrepriseRepository typeEntrepriseRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.typeEntrepriseRepository = typeEntrepriseRepository;
    }

    @Override
    public EntrepriseSaveDto save(EntrepriseSaveDto entrepriseDto) {
        // 🔍 1. Validation
        List<String> errors = EntreprisesValidator.validate(entrepriseDto);
        if (!errors.isEmpty()) {
            throw new InvalEntityException(
                    "Toutes les informations ne sont pas renseignées",
                    ErrorCodes.ENTREPRISE_NOT_VALID,
                    errors
            );
        }

        Entreprises entity;

        if (entrepriseDto.getUuid() != null) {
            // 📝 Modification : récupérer l'entité existante
            entity = entrepriseRepository.findByUuid(entrepriseDto.getUuid())
                    .orElseThrow(() -> new EntityNoFoundException(
                            "Entreprise non trouvée avec uuid : " + entrepriseDto.getUuid(),
                            ErrorCodes.ENTREPRISE_NOT_FOUND
                    ));

            // 🔁 Mettre à jour les champs
            entity.setNomEntreprise(entrepriseDto.getNomEntreprise());
            entity.setEmail(entrepriseDto.getEmail());
            entity.setCodeFiscale(entrepriseDto.getCodeFiscale());
            entity.setNumero(entrepriseDto.getNumero());
            entity.setDescription(entrepriseDto.getDescription());
            // entity.setAddresse(EntrepriseMapper.toEntity(entrepriseDto).getAddresse());
            entity.setPhotoEntreprise(entrepriseDto.getPhotoEntreprise());
            entity.setSiteWebUrl(entrepriseDto.getSiteWebUrl());
            if (entity.getAddresse() != null) {
                Addresse addresse = new Addresse();
                addresse.setAddresse1(entrepriseDto.getAddresse().getAddresse1());
                addresse.setVille(entrepriseDto.getAddresse().getVille());
                addresse.setPays(entrepriseDto.getAddresse().getPays());
                addresse.setCodePostale(entrepriseDto.getAddresse().getCodePostale());
            }
        } else {
            // 💾 Création
            entity = EntrepriseMapper.toEntity(entrepriseDto);
        }
        // Vérifier et associer le type de boutique
        if (entrepriseDto.getTypeEntreprises() != null &&
                entrepriseDto.getTypeEntreprises().getUuid() != null) {
            TypeEntreprises typeEntreprises =
                    typeEntrepriseRepository.findByUuid(entrepriseDto.getTypeEntreprises().getUuid())
                            .orElseThrow(() -> new RuntimeException("Type de boutique non trouvé"));
            entity.setTypeEntreprises(typeEntreprises);
        }

        // 🔐 Sauvegarde
        Entreprises savedEntreprise = entrepriseRepository.save(entity);
        EntrepriseSaveDto savedDto = EntrepriseMapper.fromEntity(savedEntreprise);

        // 👤 Gestion de l'utilisateur et du rôle seulement si création
        if (entrepriseDto.getUuid() == null) {
            UtilisateurSaveDto utilisateur = fromEntreprise(savedDto);
            UtilisateurSaveDto savedUser = utilisateurService.save(utilisateur);

            RoleSaveDto role = RoleSaveDto.builder()
                    .nomRole("ADMIN")
                    .utilisateur(savedUser)
                    .build();

            roleRepository.save(RoleMapper.toEntity(role));
        }

        return savedDto;
    }

    private UtilisateurSaveDto fromEntreprise(EntrepriseSaveDto entrepriseDto) {
        return UtilisateurSaveDto.builder()
                .nomPrenomUtilisateurs(entrepriseDto.getNomEntreprise())
                .email(entrepriseDto.getEmail())
                .pwd(defaultPwd()) // ✅ mot de passe par défaut
                .entreprise(entrepriseDto)
                .dateNaissance(Instant.now()) // ⚠️ optionnel — à revoir selon ton modèle
                .addresse(entrepriseDto.getAddresse())
                .photoUtilisateurs(entrepriseDto.getPhotoEntreprise())
                .build();
    }


    private String defaultPwd() {
        return "8345karifa@@";
    }

    @Override
    public EntrepriseListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant client est introuvable");
        }

        Optional<Entreprises> entreprises = entrepriseRepository.findByUuid(uuid);

        return entreprises
                .map(EntrepriseMapper::toListDto)
                .orElseThrow(() -> new EntityNoFoundException(
                        "le entreprise avec identifiant : " + uuid + "n'existe pas dans la base de donnee",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public List<EntrepriseListDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EntrepriseListDto> getEntrepriseByTypeEntreprises(UUID typeUuid) {
        if (typeUuid == null) {
            log.error("type entreprise  introuvable");
            throw new IllegalArgumentException("UUID du type entreprise ne peut pas être null");
        }

        // Récupération ou exception si non trouvé
        TypeEntreprises typeEntreprises = typeEntrepriseRepository.findByUuid(typeUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun Type de boutique avec uuid " + typeUuid + " n'a été trouvé",
                        ErrorCodes.CONFIG_NOT_FOUND
                ));

        // Récupérer les entreprises liées à ce type
        List<Entreprises> entreprises = entrepriseRepository.findByTypeEntreprises(typeEntreprises);
        // Mapper vers DTO
        return entreprises.stream()
                .map(EntrepriseMapper::toListDto) // méthode qui convertit une Entreprises en EntrepriseListDto
                .collect(Collectors.toList());
    }

    @Override
    public List<EntrepriseListDto> getEntrepriseByVille(String ville) {
        if (ville == null) {
            log.error("La ville ne peut pas être null");
            throw new IllegalArgumentException("La ville ne peut pas être null");
        }

        // Récupération des entreprises par ville
        List<Entreprises> entreprises = entrepriseRepository.findByVille(ville);

        if (entreprises.isEmpty()) {
            throw new EntityNoFoundException(
                    "Aucune entreprise trouvée pour la ville : " + ville,
                    ErrorCodes.CONFIG_NOT_FOUND
            );
        }

        // Mapper vers DTO
        return entreprises.stream()
                .map(EntrepriseMapper::toListDto) // méthode qui convertit une Entreprises en EntrepriseListDto
                .collect(Collectors.toList());
    }


    @Override
    public List<EntrepriseListDto> getEntrepriseByStatut(StatutEntreprise statut) {
        if (statut == null) {
            log.error("Le statut de l'entreprise ne peut pas être null");
            throw new IllegalArgumentException("Le statut de l'entreprise ne peut pas être null");
        }

        // Récupération des entreprises par statut
        List<Entreprises> entreprises = entrepriseRepository.findByStatut(statut);

        if (entreprises.isEmpty()) {
            throw new EntityNoFoundException(
                    "Aucune entreprise trouvée avec le statut : " + statut,
                    ErrorCodes.CONFIG_NOT_FOUND
            );
        }

        // Mapper vers DTO
        return entreprises.stream()
                .map(EntrepriseMapper::toListDto) // méthode qui convertit une Entreprises en EntrepriseListDto
                .collect(Collectors.toList());
    }


    @Override
    public void deleteByUuid(UUID uuid) {
        if (uuid == null) {
            throw new InvalEntityException("L'identifiant UUID ne peut pas être nul");
        }

        var entreprise = entrepriseRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNoFoundException("Entreprise non trouvée avec uuid : " + uuid));

        int countUsers = utilisateurRepository.countByEntreprise_Uuid(uuid);
        if (countUsers > 0) {
            throw new InvalidOperationException("Impossible de supprimer : des utilisateurs sont encore liés à cette entreprise");
        }
        entrepriseRepository.delete(entreprise);

        log.info("✅ Entreprise supprimée avec succès : {}", uuid);
    }
}
