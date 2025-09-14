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

import java.util.Arrays;
import java.util.List;

//@Component
//@Slf4j
//public class ApplicationRequestFilter extends OncePerRequestFilter {
//
//
//    private JwtUtils jwtUtils; // classe utilitaire qui gère la génération et la validation du token
//    private ApplicationUserDetailsService userDetailsService; // ton service qui charge l'utilisateur en DB
//
//    @Autowired
//    public ApplicationRequestFilter(ApplicationUserDetailsService userDetailsService, JwtUtils jwtUtils) {
//        this.userDetailsService = userDetailsService;
//        this.jwtUtils = jwtUtils;
//    }
//
//    //  @Autowired
////  public ApplicationRequestFilter(ApplicationUserDetailsService userDetailsService) {
////      this.userDetailsService = userDetailsService;
////  }
//
//    @Override
//    protected void doFilterInternal
//            (HttpServletRequest request,
//              HttpServletResponse response,
//              FilterChain chain) throws ServletException, IOException, java.io.IOException {
//
//        String path = request.getRequestURI();
//        // ✅ Laisser passer les endpoints publics
//        if (path.startsWith("/gestiondesstocks/v1/auth/authenticate") ||
//                path.startsWith("/swagger-ui/") ||
//                path.startsWith("/v3/api-docs/") ||
//                path.startsWith("/gestiondesstocks/v1/utilisateurs/email/")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        // 1️⃣ Récupération du header Authorization
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader == null) {
//        }
//        String username = null;
//        String jwt = null;
//        String idEntreprise = null;
//
//        // 2️⃣ Vérifier si le header contient un Bearer token
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7); // Supprimer "Bearer "
//            username = jwtUtils.extractUsername(jwt); // Récupérer l'email (username) du token
//            idEntreprise = jwtUtils.extractIdEntreprise(jwt);
//        }else {
//            log.warn("⚠️ Authorization header mal formé ou manquant");
//        }
//
//        // 3️⃣ Vérifier que l'utilisateur n'est pas déjà authentifié
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            // 4️⃣ Valider le token JWT
//            if (jwtUtils.validateToken(jwt, userDetails)) {
//                log.info("✅ Token valide pour utilisateur: {}", username);
//
//                // Créer une authentification Spring Security
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // Mettre l'utilisateur dans le contexte de sécurité
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//            else {
//                log.error("❌ Token invalide ou expiré pour utilisateur: {}", username);
//            }
//        }
//
//       MDC.put("idEntreprise",idEntreprise);
//        // 5️⃣ Passer au filtre suivant
//        chain.doFilter(request, response);
//    }
//}
@Component
@Slf4j
public class ApplicationRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ApplicationUserDetailsService userDetailsService;

    // Liste des endpoints publics - mieux de centraliser cette configuration
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException, java.io.IOException {

        String path = request.getRequestURI();

        // ✅ Vérifier si l'endpoint est public
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 1️⃣ Récupération du header Authorization
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("⚠️ Authorization header manquant ou mal formé pour: {}", path);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token manquant ou invalide");
                return;
            }

            String jwt = authHeader.substring(7); // Supprimer "Bearer "
            String username = jwtUtils.extractUsername(jwt);
            String idEntreprise = jwtUtils.extractIdEntreprise(jwt);

            // Validation basique du token
            if (username == null) {
                log.warn("❌ Impossible d'extraire le username du token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
                return;
            }

            // 2️⃣ Vérifier que l'utilisateur n'est pas déjà authentifié
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 3️⃣ Valider le token JWT
                if (jwtUtils.validateToken(jwt, userDetails)) {
                    log.debug("✅ Token valide pour utilisateur: {}", username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Ajouter l'idEntreprise au MDC pour le logging
                    MDC.put("idEntreprise", idEntreprise != null ? idEntreprise : "unknown");

                } else {
                    log.error("❌ Token invalide ou expiré pour utilisateur: {}", username);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expiré ou invalide");
                    return;
                }
            }

            // 4️⃣ Passer au filtre suivant
            chain.doFilter(request, response);

        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement de l'authentification: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur d'authentification");
        } finally {
            // Nettoyer le MDC à la fin de la requête
            MDC.remove("idEntreprise");
        }
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }
}
