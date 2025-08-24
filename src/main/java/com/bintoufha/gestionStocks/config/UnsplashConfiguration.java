package com.bintoufha.gestionStocks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnsplashConfiguration {

    @Value("${unsplash.apiKey}")
    private String apiKey;

//    @Value("${unsplash.apiSecret}")
    private String apiSecret;

//    public  Unsplash getU {
//
//    }
}