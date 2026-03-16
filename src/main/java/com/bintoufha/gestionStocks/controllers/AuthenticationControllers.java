package com.bintoufha.gestionStocks.controllers;

import com.bintoufha.gestionStocks.dto.auth.AuthenticationRequest;
import com.bintoufha.gestionStocks.dto.auth.AuthenticationResponse;
import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import com.bintoufha.gestionStocks.services.auth.ApplicationUserDetailsService;
import com.bintoufha.gestionStocks.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bintoufha.gestionStocks.utils.Constante.*;

@RestController
@Slf4j
public class AuthenticationControllers {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    /** ----------------------------------------------------------
     *  🔑 Endpoint de login
     * ---------------------------------------------------------- */
    @PostMapping(
            value = AUTHENTICATE_ENDPOINT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            // 1️⃣ Authentification via Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2️⃣ Charger l’utilisateur enrichi
            ExtendedUser userDetails =
                    (ExtendedUser) userDetailsService.loadUserByUsername(request.getUsername());

            // 3️⃣ Générer le token JWT avec claims (uuid, idEntreprise, boutiqueId)
            final String token = jwtUtils.generateToken(userDetails);

            // 4️⃣ Retourner la réponse
            return ResponseEntity.ok(
                    AuthenticationResponse.builder()
                            .accessToken(token)
                            .message("Authentification réussie")
                            .build()
            );

        } catch (BadCredentialsException ex) {
            log.warn("Tentative de login échouée pour {}", request.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(AuthenticationResponse.builder()
                            .message("Identifiants invalides")
                            .build());
        }
    }

    /** ----------------------------------------------------------
     *  🔎 Vérification du token
     * ---------------------------------------------------------- */
    @PostMapping(
            value = AUTHENTICATE_ENDPOINT_VERIFY_TOKEN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Boolean>> verifyToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        boolean valid = jwtUtils.isTokenValid(token);
        return ResponseEntity.ok(Map.of("valid", valid));
    }

    /** ----------------------------------------------------------
     *  👤 Récupérer l’utilisateur courant
     * ---------------------------------------------------------- */
    @GetMapping(
            value = AUTHENTICATE_ENDPOINT_ME,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }

        String username = authentication.getName();
        ExtendedUser userDetails =
                (ExtendedUser) userDetailsService.loadUserByUsername(username);

        return ResponseEntity.ok(userDetails);
    }
}
