package com.bintoufha.gestionStocks.config;


import com.bintoufha.gestionStocks.utils.Constante;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

import static com.bintoufha.gestionStocks.utils.Constante.APP_ROOT;

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
                .servers(List.of(
                new Server().url("http://localhost:8081") // 👈 ici, au niveau OpenAPI
        ));
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
