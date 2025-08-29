package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.EntrepriseDto;
import com.bintoufha.gestionStocks.dto.RolesDto;
import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.model.Entreprises;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.repository.RoleRepository;
import com.bintoufha.gestionStocks.services.EntrepriseService;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import com.bintoufha.gestionStocks.validator.EntreprisesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private final DefaultErrorAttributes errorAttributes;
    private final UtilisateurService utilisateurService;
    private EntrepriseRepository entrepriseRepository;
    private RoleRepository roleRepository;

    @Autowired
    public EntrepriseServiceImpl(
            DefaultErrorAttributes errorAttributes,
            EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService) {
        this.entrepriseRepository = entrepriseRepository;
        this.errorAttributes = errorAttributes;
        this.utilisateurService = utilisateurService;
        this.roleRepository = roleRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
        List<String> errors = EntreprisesValidator.validate(entrepriseDto);
        if (!errors.isEmpty()) {
            log.error("donnee non valide");
            throw new InvalEntityException("tout les information ne sont pas renseigner",
                    ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(entrepriseDto))
        );

//        enregistre par defaut un utilisateur pour entreprise nouvellement enregistre
        UtilisateursDto utilisateurs = fromEntreprise(savedEntreprise);

        UtilisateursDto savedUser = utilisateurService.save(utilisateurs);

        RolesDto roles = RolesDto.builder()
                .nomRole("ADMIN")
                .utilisateur(savedUser)
                .build();
        roleRepository.save(RolesDto.toEntity(roles));

        return savedEntreprise;
    }

    private UtilisateursDto fromEntreprise(EntrepriseDto entrepriseDto){
        return UtilisateursDto.builder()
                .nomPrenomUtilisateurs(entrepriseDto.getNomEntreprise())
                .email(entrepriseDto.getEmail())
                .pwd(defaultPwd())
                .entreprise(entrepriseDto)
                .dateNaissance(Instant.now())
                .addresse(entrepriseDto.getAddresse())
                .build();
    }

    private String defaultPwd(){
        return "8345karifa@@";
    }

    @Override
    public EntrepriseDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Identifiant client est introuvable");
        }

        Optional<Entreprises> entreprises = entrepriseRepository.findByUuid(uuid);

        return entreprises
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "le client avec identifiant : " + uuid + "n'existe pas dans la base de donnee",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("identifiants invalide");
        }
        entrepriseRepository.findByUuid(uuid);
    }
}
