package com.bintoufha.gestionStocks.config;

import com.bintoufha.gestionStocks.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ApplicationRequestFilter applicationRequestFilter;

    @Autowired
    public SecurityConfiguration(
            ApplicationUserDetailsService applicationUserDetailsService,
            ApplicationRequestFilter applicationRequestFilter
    ) {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.applicationRequestFilter = applicationRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // ✅ Activation CORS
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques
                        .requestMatchers("/gestiondesstocks/v1/auth/authenticate").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/gestiondesstocks/v1/utilisateurs/email/**").permitAll()
                        .requestMatchers("/gestiondesstocks/v1/entreprises/**").permitAll()

                        // Routes protégées par rôles
                        .requestMatchers("/gestiondesstocks/v1/admin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/gestiondesstocks/v1/entreprises/**").hasAnyRole("ADMIN_BOUTIQUE", "SUPER_ADMIN")
                        .requestMatchers("/gestiondesstocks/v1/stock/**").hasAnyRole("EMPLOYE_STOCK", "RESPONSABLE_STOCK",
                                "ADMIN_BOUTIQUE", "SUPER_ADMIN")
                        .requestMatchers("/gestiondesstocks/v1/vente/**").hasAnyRole("EMPLOYE_VENTE", "ADMIN_BOUTIQUE", "SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Configuration CORS (Backend accepte Angular)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
