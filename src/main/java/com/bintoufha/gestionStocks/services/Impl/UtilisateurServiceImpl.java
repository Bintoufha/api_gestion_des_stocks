package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.utilisateur.ChangerMotDePasseUtilisateurDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.mapper.UtilisateurMapper;
import com.bintoufha.gestionStocks.model.*;
import com.bintoufha.gestionStocks.repository.EntrepriseRepository;
import com.bintoufha.gestionStocks.repository.RoleRepository;
import com.bintoufha.gestionStocks.repository.UserRoleBoutiqueRepository;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import com.bintoufha.gestionStocks.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateursRepository utilisateursRepository;
    private EntrepriseRepository entrepriseRepository;
    private RoleRepository roleRepository;
    private UserRoleBoutiqueRepository userRoleBoutiqueRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(
            UtilisateursRepository utilisateursRepository,
            EntrepriseRepository entrepriseRepository,
            RoleRepository roleRepository,
            UserRoleBoutiqueRepository userRoleBoutiqueRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.utilisateursRepository = utilisateursRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleRepository = roleRepository;
        this.userRoleBoutiqueRepository =userRoleBoutiqueRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurSaveDto save(UtilisateurSaveDto utilisateursDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateursDto);
        if (!errors.isEmpty()) {
            log.error("utilisateurs n'est pas valide {}", utilisateursDto);
            throw new InvalEntityException(
                    "cette utilisateur n'est pas valid verifier les informations ",
                    ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }

        if (userAlreadyExists(utilisateursDto.getEmail())) {
            throw new InvalEntityException("un autre utilisateur avec le meme email existe",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la base de donnée"));
        }

        utilisateursDto.setPwd(passwordEncoder.encode(utilisateursDto.getPwd()));

        return UtilisateurMapper.fromEntity(
                utilisateursRepository.save(
                        UtilisateurMapper.toEntity(utilisateursDto)
                )
        );
    }


    @Override
    public UtilisateurListDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateursRepository.findByUuid(uuid)
                .map(UtilisateurMapper::toListDto)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + uuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurListDto> findAll() {
        return utilisateursRepository.findAll()
                .stream()
                .map(utilisateurs -> {
                    // ou via TarificationService si besoin
                    return UtilisateurMapper.toListDto(utilisateurs);
                })
                .toList();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Utilisateur ID is null");
            return;
        }
        utilisateursRepository.deleteById(uuid);

    }

    @Override
    public void assignRolesToUser(UUID utilisateurUuid,
                                  Set<UUID> roleUuids,
                                  UUID entrepriseUuid,
                                  UUID assignedByUtilisateurUuid
    ) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + utilisateurUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

        Utilisateurs assignedBy = utilisateursRepository.findByUuid(assignedByUtilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                                "Aucun assignateur avec l'ID = " + assignedByUtilisateurUuid + " n' ete trouve dans la BDD",
                                ErrorCodes.UTILISATEUR_NOT_FOUND));

        Entreprises entreprises = null;
        if (entrepriseUuid != null) {
            entreprises = entrepriseRepository.findByUuid(entrepriseUuid)
                    .orElseThrow(() -> new EntityNoFoundException(
                            "Aucun entreprise avec l'ID = " + entrepriseUuid + " n' ete trouve dans la BDD",
                            ErrorCodes.ENTREPRISE_NOT_FOUND));
        }

        for (UUID roleUuid : roleUuids) {
            Roles role = roleRepository.findById(roleUuid)
                    .orElseThrow(() -> new EntityNoFoundException(
                            "Aucun entreprise avec l'ID = " + entrepriseUuid + " n' ete trouve dans la BDD",
                            ErrorCodes.ROLE_NOT_FOUND));

            // Vérifier la cohérence rôle/boutique
            if (role.getScope() == RoleScope.ENTREPRISES && entreprises == null) {
                throw new RuntimeException("Le rôle " + role.getNomRole() + " nécessite une entreprises");
            }
            if (role.getScope() == RoleScope.GLOBAL && entreprises != null) {
                throw new RuntimeException("Le rôle " + role.getNomRole() + " est global, pas de entreprises nécessaire");
            }

            // Chercher assignation existante
            Optional<UserRoleEntreprise> existing = userRoleBoutiqueRepository
                    .findByUtilisateursAndRoleAndEntreprise(user, role, entreprises);

            if (existing.isPresent()) {
                // Réactiver si nécessaire
                UserRoleEntreprise urb = existing.get();
                if (!urb.isActive()) {
                    urb.setActive(true);
                    urb.setExpiresAt(null);
                    userRoleBoutiqueRepository.save(urb);
                }
            } else {
                // Créer nouvelle assignation
                UserRoleEntreprise urb = new UserRoleEntreprise();
                urb.setUtilisateurs(user);
                urb.setRole(role);
                urb.setEntreprises(entreprises);
                urb.setAssignedBy(assignedBy);
                urb.setActive(true);
                userRoleBoutiqueRepository.save(urb);
            }
        }
    }

    @Override
    public void removeRoleFromUser(UUID utilisateurUuid, UUID roleUuid, UUID entrepriseUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + utilisateurUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

        Roles role = roleRepository.findById(roleUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun entreprise avec l'ID = " + entrepriseUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.ROLE_NOT_FOUND));

        Entreprises entreprises = null;
        if (entrepriseUuid != null) {
            entreprises = entrepriseRepository.findByUuid(entrepriseUuid)
                    .orElseThrow(() -> new EntityNoFoundException(
                            "Aucun entreprise avec l'ID = " + entrepriseUuid + " n' ete trouve dans la BDD",
                            ErrorCodes.ENTREPRISE_NOT_FOUND));
        }

        Optional<UserRoleEntreprise> urb = userRoleBoutiqueRepository
                .findByUtilisateursAndRoleAndEntreprise(user, role, entreprises);

        if (urb.isPresent()) {
            // Désactiver plutôt que supprimer pour l'audit
            UserRoleEntreprise userRoleEntreprise = urb.get();
            userRoleEntreprise.setActive(false);
            userRoleBoutiqueRepository.save(userRoleEntreprise);
        }
    }

    @Override
    public void deactivateUser(UUID utilisateurUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + utilisateurUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

        user.setActif(false);
        utilisateursRepository.save(user);
    }

    @Override
    public void activateUser(UUID utilisateurUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + utilisateurUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

        user.setActif(true);
        utilisateursRepository.save(user);
    }

    @Override
    public List<UtilisateurListDto> getUsersByBoutique(UUID entrepriseUuid) {
        return utilisateursRepository.findByEntrepriseUuid(entrepriseUuid)
                .stream()
                .map(utilisateurs -> {
                    // ou via TarificationService si besoin
                    return UtilisateurMapper.toListDto(utilisateurs);
                })
                .toList();
    }

    @Override
    public List<UtilisateurListDto> getUsersByRole(String roleName) {
        return utilisateursRepository.findByRoleName(roleName)
                .stream()
                .map(utilisateurs -> {
                    // ou via TarificationService si besoin
                    return UtilisateurMapper.toListDto(utilisateurs);
                })
                .toList();
    }

    @Override
    public List<Roles> getUserRoles(UUID utilisateurUuid) {
        List<UserRoleEntreprise> urbs = userRoleBoutiqueRepository.findActiveByUtilisateursUuid(utilisateurUuid);
        return urbs.stream()
                .map(UserRoleEntreprise::getRole)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUserPermissions(UUID utilisateurUuid) {
        List<UserRoleEntreprise> urbs = userRoleBoutiqueRepository.findActiveByUtilisateursUuid(utilisateurUuid);
        return urbs.stream()
                .flatMap(urb -> urb.getRole().getAllPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean userHasRole(UUID utilisateurUuid, String roleName) {
        return userRoleBoutiqueRepository.userHasRole(utilisateurUuid, roleName);    }

    @Override
    public boolean userHasRoleForEntreprise(UUID utilisateurUuid, String roleName, UUID entrepriseUuid) {
        return userRoleBoutiqueRepository.userHasRoleForBoutique(utilisateurUuid, roleName, entrepriseUuid);
    }

    @Override
    public void updateLastLogin(UUID utilisateurUuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(utilisateurUuid)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + utilisateurUuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

        user.setDerniereConnexion(LocalDateTime.now());
        utilisateursRepository.save(user);
    }

    @Override
    public UtilisateurByEmailDto findByEmail(String email) {
        return utilisateursRepository.findUtilisateursByEmailIgnoreCase(email)
                .map(UtilisateurMapper::toEmailtDto)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'email fourni",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    // methode pour change le mot de passe utilisateur
    @Override
    public UtilisateurSaveDto ChangePwd(ChangerMotDePasseUtilisateurDto pwdChange) {

        validate(pwdChange);

        // ✅ Conversion String -> UUID
        UUID uuid = pwdChange.getUuid();
        log.info("UUID reçu (objet UUID) : {}", uuid);

        // ✅ Recherche utilisateur par UUID
        Optional<Utilisateurs> utilisateursOptional = utilisateursRepository.findByUuid(uuid);
        if (utilisateursOptional.isEmpty()) {
            log.warn("Aucun utilisateur n'a été trouvé avec identifiant {}", uuid);
            throw new EntityNoFoundException(
                    "Aucun utilisateur n'a été trouvé avec identifiant " + uuid,
                    ErrorCodes.UTILISATEUR_NOT_FOUND
            );
        }

        Utilisateurs utilisateurs = utilisateursOptional.get();

        // ✅ Changement du mot de passe
        utilisateurs.setPwd(pwdChange.getPwd());

        // ✅ Sauvegarde et retour du DTO
        Utilisateurs saved = utilisateursRepository.save(utilisateurs);
        log.info("Mot de passe modifié pour l'utilisateur avec UUID {}", uuid);

        return UtilisateurMapper.fromEntity(saved);
    }

    // Méthode pour vérifier si utilisateur existe
    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = utilisateursRepository.findUtilisateursByEmailIgnoreCase(email);
        return user.isPresent();
    }

    // Validateur de mot de passe utilisateur avant le changement
    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException(
                    "Aucune information n'a été fournie pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (dto.getUuid() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException(
                    "ID utilisateur null : Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (!StringUtils.hasLength(dto.getPwd()) || !StringUtils.hasLength(dto.getPwdConfirmer())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException(
                    "Mot de passe utilisateur null : Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
        if (!dto.getPwd().equals(dto.getPwdConfirmer())) {
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe différents");
            throw new InvalidOperationException(
                    "Mots de passe utilisateur non conformes : Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
    }

}
