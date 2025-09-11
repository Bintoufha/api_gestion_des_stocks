package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.dto.ChangerMotDePasseUtilisateurDto;
import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalEntityException;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import com.bintoufha.gestionStocks.validator.UtilisateurValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateursRepository utilisateursRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(
            UtilisateursRepository utilisateursRepository, PasswordEncoder passwordEncoder
    ) {
        this.utilisateursRepository = utilisateursRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateursDto save(UtilisateursDto utilisateursDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateursDto);
        if (!errors.isEmpty()) {
            log.error("utilisateurs n'est pas valide {}", utilisateursDto);
            throw new InvalEntityException("cette utilisateur n'est pas valid verifier les informations ", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }

        if (userAlreadyExists(utilisateursDto.getEmail())) {
            throw new InvalEntityException("un autre utilisateur avec le meme email existe",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la base de donnée"));
        }

        utilisateursDto.setPwd(passwordEncoder.encode(utilisateursDto.getPwd()));

        return UtilisateursDto.fromEntity(
                utilisateursRepository.save(
                        UtilisateursDto.toEntity(utilisateursDto)
                )
        );
    }


    @Override
    public UtilisateursDto findByUuid(UUID uuid) {
        if (uuid == null) {
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateursRepository.findById(uuid)
                .map(UtilisateursDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'ID = " + uuid + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateursDto> findAll() {
        return utilisateursRepository.findAll().stream()
                .map(UtilisateursDto::fromEntity)
                .collect(Collectors.toList());
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
    public UtilisateursDto findByEmail(String email) {
        return utilisateursRepository.findUtilisateursByEmailIgnoreCase(email)
                .map(UtilisateursDto::fromEntity)
                .orElseThrow(() -> new EntityNoFoundException(
                        "Aucun utilisateur avec l'email fourni",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    // methode pour change le mot de passe utilisateur
    @Override
    public UtilisateursDto ChangePwd(ChangerMotDePasseUtilisateurDto pwdChange) {
        validate(pwdChange);
        Optional<Utilisateurs>utilisateursOptional = utilisateursRepository.findByUuid(pwdChange.getUuid());
        if (utilisateursOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a été trouve avec identifiant"+pwdChange.getUuid());
            throw new EntityNoFoundException("Aucun utilisateur n'a été trouve avec identifaint "+ pwdChange.getUuid(),
                    ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateurs utilisateurs = utilisateursOptional.get();
        utilisateurs.setPwd(pwdChange.getPwd());

        return UtilisateursDto.fromEntity(
                utilisateursRepository.save(utilisateurs)
        );
    }

    // methode de verifier si utilisateur existe
    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = utilisateursRepository.findUtilisateursByEmailIgnoreCase(email);
        return user.isPresent();
    }
    // validateur de mot de passe utilisateur avant le changement
    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException("Aucune information n'a ete fourni pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getUuid() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getPwd()) || !StringUtils.hasLength(dto.getPwdConfirmer())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException("Mot de passe utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!dto.getPwd().equals(dto.getPwdConfirmer())) {
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
            throw new InvalidOperationException("Mots de passe utilisateur non conformes:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
