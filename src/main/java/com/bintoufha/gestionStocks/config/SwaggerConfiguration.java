package com.bintoufha.gestionStocks.config;


import com.bintoufha.gestionStocks.utils.Constante;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class SwaggerConfiguration {

    @Bean
    public OpenAPI gestionStocksOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion des Stocks API")
                        .version("1.0.0")
                        .description("API pour gérer les produits, les stocks, les entrées/sorties et les fournisseurs.")
                        .contact(new Contact()
                                .name("Karifa")
                                .email("bintoufha@gmail.com")
                                .url("https://github.com/karifa")
                        )

                )
                .addServersItem(new Server().url("http://localhost:8083")) // 👈 Serveur
                // 🔑 Ajout sécurité pour le bouton Authorize
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    private void servers(List<Server> url) {
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch(Constante.APP_ROOT + "/**")
                .displayName("Endpoints Publics")
                .build();
    }
}
