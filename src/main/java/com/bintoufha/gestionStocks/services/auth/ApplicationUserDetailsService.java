package com.bintoufha.gestionStocks.services.auth;

import com.bintoufha.gestionStocks.dto.UtilisateursDto;
import com.bintoufha.gestionStocks.exception.EntityNoFoundException;
import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import com.bintoufha.gestionStocks.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UtilisateursDto utilisateurs = utilisateurService.findByEmail(email);
        //log.debug("Mot de passe hashé en base: {}", utilisateurs.getPassword());
        if (utilisateurs == null) {
            log.error("❌ Utilisateur introuvable pour email: {}", email);
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        log.debug("✅ Utilisateur trouvé: {} | UUID Entreprise: {}",
                utilisateurs.getEmail(),
                utilisateurs.getEntreprise().getUuid());
        List<SimpleGrantedAuthority> authorities =List.of(
                new SimpleGrantedAuthority("ROLE_USER") // rôle par défaut temporaire
        );


//        utilisateurs.getRoles().forEach(
//                roles -> {
//                    log.debug("🎭 Ajout du rôle: ROLE_{}", roles.getNomRole());
//                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getNomRole()));
//                }
//        );
        //log.debug("🎭 Ajout du rôle: ROLE_{}", roles.getNomRole()),
//                roles -> authorities.add(
//                        new SimpleGrantedAuthority("ROLE_" + roles.getNomRole()
//                        )));


        return new ExtendedUser(
                utilisateurs.getEmail(),
                utilisateurs.getPwd(),
                authorities,
                utilisateurs.getEntreprise().getUuid()
        );

    }
}
