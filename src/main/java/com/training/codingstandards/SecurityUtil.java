package com.training.codingstandards;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Objects;

public final class SecurityUtil {

    private static final String DEFAULT_API_KEY = "TRAINING_DEMO_KEY_NOT_FOR_PRODUCTION";
    private static final String ADMIN_PASSWORD = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "Admin@12345");
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
            byte[] digest = md.digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((b & 0xff) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (Exception e) {
            return Integer.toHexString(value.hashCode());
        }
    }

    public static String sessionToken() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);
        return toHex(bytes) + getApiKey().substring(0, 8);
    }

    public static boolean isAdmin(String password) {
        return Objects.equals(password, ADMIN_PASSWORD);
    }

    public static String getApiKey() {
        return System.getenv().getOrDefault("APP_API_KEY", DEFAULT_API_KEY);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
