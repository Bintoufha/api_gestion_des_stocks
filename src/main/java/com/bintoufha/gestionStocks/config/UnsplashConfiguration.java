package com.bintoufha.gestionStocks.config;

import com.bintoufha.gestionStocks.services.Impl.UnsplashImageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class UnsplashConfiguration {

    @Value("${unsplash.apiKey}")
    private String accessKey;

    @Value("${unsplash.apiSecret}")
    private String secretKey;

    @Value("${unsplash.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient unsplashWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Client-ID " + accessKey)
                .defaultHeader("Accept-Version", "v1")
                .build();
    }
    @Bean
    public UnsplashImageService unsplashService(WebClient unsplashWebClient) {
        return new UnsplashImageServiceImpl(unsplashWebClient);
    }
}