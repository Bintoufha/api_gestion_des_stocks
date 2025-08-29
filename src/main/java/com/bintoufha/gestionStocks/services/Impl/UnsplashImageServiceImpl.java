package com.bintoufha.gestionStocks.services.Impl;

import com.bintoufha.gestionStocks.services.UnsplashImageService;
import lombok.Value;

public class UnsplashImageServiceImpl implements UnsplashImageService {
    @Override
    public String searchImages(String query, int page, int perPage) {
        return "";
    }

//    private final WebClient webClient;
//
//    public UnsplashImageServiceImpl(@Value("${unsplash.base-url}") String baseUrl,
//                                 @Value("${unsplash.api-key}") String apiKey) {
//        this.webClient = WebClient.builder()
//                .baseUrl(baseUrl)
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Client-ID " + apiKey)
//                .build();
//    }
//
//    @Override
//    public List<ImageResult> searchImages(String query, int page, int perPage) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/search/photos")
//                        .queryParam("query", query)
//                        .queryParam("page", page)
//                        .queryParam("per_page", perPage)
//                        .build())
//                .retrieve()
//                .bodyToMono(JsonNode.class)
//                .map(json -> {
//                    JsonNode results = json.get("results");
//                    return StreamSupport.stream(results.spliterator(), false)
//                            .map(node -> new ImageResult(
//                                    node.get("urls").get("regular").asText(),
//                                    node.get("urls").get("thumb").asText(),
//                                    node.get("user").get("name").asText(),
//                                    node.get("user").get("links").get("html").asText()
//                            ))
//                            .collect(Collectors.toList());
//                })
//                .block();
//    }
}
