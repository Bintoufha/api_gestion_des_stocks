package com.bintoufha.gestionStocks.services.auth;

import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurByEmailDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurListDto;
import com.bintoufha.gestionStocks.dto.utilisateur.UtilisateurSaveDto;
import com.bintoufha.gestionStocks.model.Utilisateurs;
import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import com.bintoufha.gestionStocks.repository.UtilisateursRepository;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UtilisateurByEmailDto utilisateurs = utilisateurService.findByEmail(email);
        //log.debug("Mot de passe hashé en base: {}", utilisateurs.getPassword());
        if (utilisateurs == null) {
            log.error("❌ Utilisateur introuvable pour email: {}", email);
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        if (!utilisateurs.isActif()) {
            throw new UsernameNotFoundException("Utilisateur avec cette email " +utilisateurs.getEmail() +" est désactivé: ");
        }

        // Récupérer les rôles et permissions
        Set<SimpleGrantedAuthority> authorities = utilisateurs.getRoles().stream()
                .flatMap(role -> {
                    // Ajouter le rôle (préfixé par ROLE_)
                    var roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getNomRole());
                    // Autorités des permissions associées au rôle
                    var permissionAuthorities = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                            .collect(Collectors.toSet());
                    // Ajouter le rôle lui-même
                    permissionAuthorities.add(roleAuthority);
                    return permissionAuthorities.stream();
                }).collect(Collectors.toSet());
        // Retourner un ExtendedUser (ton implémentation custom)
        return new ExtendedUser(
                utilisateurs.getEmail(),
                utilisateurs.getNomPrenomUtilisateurs(),
                utilisateurs.getPwd(),
                authorities,
                utilisateurs.getUuid(),
                utilisateurs.getEntreprise().getUuid()
        );
    }

    @Transactional
    public UserDetails loadUserByUuid(UUID uuid) {
        Utilisateurs user = utilisateursRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec id: " + uuid));
        return loadUserByUsername(user.getNomPrenomUtilisateurs());
    }

}
