package com.foodie.app.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class ApiClient {
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
            
    private static final Gson gson = new GsonBuilder().create();

    private static HttpRequest.Builder getBaseRequestBuilder(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
                
        String token = ApiConfig.getJwtToken();
        if (token != null && !token.isEmpty()) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder;
    }

    public static <T> CompletableFuture<T> get(String endpoint, Class<T> responseType) {
        HttpRequest request = getBaseRequestBuilder(endpoint).GET().build();
        return sendRequest(request, responseType);
    }

    public static <T> CompletableFuture<T> post(String endpoint, Object body, Class<T> responseType) {
        HttpRequest request = getBaseRequestBuilder(endpoint)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                .build();
        return sendRequest(request, responseType);
    }

    public static <T> CompletableFuture<T> put(String endpoint, Object body, Class<T> responseType) {
        HttpRequest request = getBaseRequestBuilder(endpoint)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                .build();
        return sendRequest(request, responseType);
    }

    public static CompletableFuture<Void> delete(String endpoint) {
        HttpRequest request = getBaseRequestBuilder(endpoint).DELETE().build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenAccept(response -> {
                    if (response.statusCode() >= 400) {
                        throw new RuntimeException("API Error: " + response.statusCode());
                    }
                });
    }

    private static <T> CompletableFuture<T> sendRequest(HttpRequest request, Class<T> responseType) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 400) {
                        throw new RuntimeException("API Error: " + response.statusCode() + " - " + response.body());
                    }
                    return gson.fromJson(response.body(), responseType);
                });
    }
}
