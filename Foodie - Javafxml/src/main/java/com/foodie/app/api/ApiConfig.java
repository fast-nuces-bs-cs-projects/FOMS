package com.foodie.app.api;

public class ApiConfig {
    public static final String BASE_URL = "http://localhost:8081";
    private static String jwtToken = null;

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static String getJwtToken() {
        return jwtToken;
    }
    
    public static boolean isAuthenticated() {
        return jwtToken != null && !jwtToken.isEmpty();
    }
}
