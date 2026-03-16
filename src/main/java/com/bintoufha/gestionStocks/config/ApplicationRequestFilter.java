package com.bintoufha.gestionStocks.config;

import com.bintoufha.gestionStocks.services.auth.ApplicationUserDetailsService;
import com.bintoufha.gestionStocks.utils.JwtUtils;
// import io.jsonwebtoken.io.IOException;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// curl http://127.0.0.1:8083/v3/api-docs -o ~/gestionStocks/target/swagger.json

@Component
@Slf4j
public class ApplicationRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ApplicationUserDetailsService userDetailsService;

    // 🔓 Endpoints publics (pas de sécurité, pas de token)
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/gestiondesstocks/v1/auth/authenticate",
            "/swagger-ui/",
            "/v3/api-docs/",
            "/gestiondesstocks/v1/utilisateurs/email/",
            "/gestiondesstocks/v1/entreprises/"
    );

    @Autowired
    public ApplicationRequestFilter(ApplicationUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        // ⭐ Autoriser immédiatement les requêtes HTTP OPTIONS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        // 🔓 Ignorer complètement les endpoints publics
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 🔓 Swagger doit passer sans auth
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            chain.doFilter(request, response);
            return;
//            sudo curl http://127.0.0.1:8083/v3/api-docs -o ~/gestionStocks/target/swagger.json
        }

        try {

            /** ----------------------------------------------------------
             *  1️⃣ Récupération des headers envoyés par Angular
             *  ---------------------------------------------------------- */
            String authHeader = request.getHeader("Authorization");
            String anneeHeader = request.getHeader("X-ANNEE");
            String idEntrepriseFromHeader = request.getHeader("X-ID-ENTREPRISE");

            // Stocker temporairement (sera mis dans le MDC ensuite)
            String anneeFiltre = (anneeHeader != null ? anneeHeader : null);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token manquant ou invalide");
                return;
            }

            String jwt = authHeader.substring(7); // remove "Bearer "
            String username = jwtUtils.extractUsername(jwt);

            if (username == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
                return;
            }

            /** ----------------------------------------------------------
             *  2️⃣ Authentification utilisateur via Spring Security
             *  ---------------------------------------------------------- */
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtils.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    /** ----------------------------------------------------------
                     *  3️⃣ Remplir MDC (Entreprise + Année)
                     *  ---------------------------------------------------------- */

                    // Entreprise : priorité au header envoyée par Angular
                    String idEntrepriseFromJwt = jwtUtils.extractIdEntreprise(jwt);
                    String idEntrepriseFinal = (idEntrepriseFromHeader != null)
                            ? idEntrepriseFromHeader
                            : idEntrepriseFromJwt;

                    MDC.put("idEntreprise", idEntrepriseFinal != null ? idEntrepriseFinal : "unknown");

                    // Année format "2024-2025"
                    if (anneeFiltre != null) {
                        MDC.put("annee", anneeFiltre);
                    }

                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expiré ou invalide");
                    return;
                }
            }

            /** ----------------------------------------------------------
             *  4️⃣ Continuer la chaîne des filtres
             *  ---------------------------------------------------------- */
            chain.doFilter(request, response);

        } finally {
            // Nettoyage du contexte (IMPORTANT)
            MDC.remove("idEntreprise");
            MDC.remove("annee");
        }
    }


    /** ----------------------------------------------------------
     * Vérifie si un endpoint est public
     * ---------------------------------------------------------- */
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }
}

