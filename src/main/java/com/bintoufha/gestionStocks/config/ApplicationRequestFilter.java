package com.bintoufha.gestionStocks.config;

import com.bintoufha.gestionStocks.services.auth.ApplicationUserDetailsService;
import com.bintoufha.gestionStocks.utils.JwtUtils;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.servlet.View;

@Component
@Slf4j
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils; // classe utilitaire qui gère la génération et la validation du token

    @Autowired
    private ApplicationUserDetailsService userDetailsService; // ton service qui charge l'utilisateur en DB

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
              HttpServletResponse response,
              FilterChain chain) throws ServletException, IOException, java.io.IOException {

        String path = request.getRequestURI();
        // ✅ Laisser passer les endpoints publics
        if (path.startsWith("/gestiondesstocks/v1/auth/authenticate") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/gestiondesstocks/v1/utilisateurs/email/")) {
            chain.doFilter(request, response);
            return;
        }
        // 1️⃣ Récupération du header Authorization
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
        }
        String username = null;
        String jwt = null;
        String idEntreprise = null;

        // 2️⃣ Vérifier si le header contient un Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Supprimer "Bearer "
            username = jwtUtils.extractUsername(jwt); // Récupérer l'email (username) du token
            idEntreprise = jwtUtils.extractIdEntreprise(jwt);
        }else {
            log.warn("⚠️ Authorization header mal formé ou manquant");
        }

        // 3️⃣ Vérifier que l'utilisateur n'est pas déjà authentifié
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4️⃣ Valider le token JWT
            if (jwtUtils.validateToken(jwt, userDetails)) {
                log.info("✅ Token valide pour utilisateur: {}", username);

                // Créer une authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Mettre l'utilisateur dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else {
                log.error("❌ Token invalide ou expiré pour utilisateur: {}", username);
            }
        }

       MDC.put("idEntreprise",idEntreprise);
        // 5️⃣ Passer au filtre suivant
        chain.doFilter(request, response);
    }
}
