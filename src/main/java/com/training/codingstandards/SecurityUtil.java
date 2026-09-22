package com.training.codingstandards;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;

public final class SecurityUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private SecurityUtil() {
        // Utility class
    }

    public static String hashIdentifier(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            return Integer.toHexString(value.hashCode());
        }
    }

    public static String sessionToken() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);
        String tokenPrefix = HexFormat.of().formatHex(bytes);
        String apiKey = getApiKey();
        return tokenPrefix + Integer.toHexString(apiKey.hashCode()).substring(0, 8);
    }

    public static boolean isAdmin(String password) {
        String configuredPassword = System.getenv("APP_ADMIN_PASSWORD");
        return configuredPassword != null && configuredPassword.equals(password);
    }

    public static String getApiKey() {
        String apiKey = System.getenv("APP_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            return "training-demo-key";
        }
        return apiKey;
    }
}
