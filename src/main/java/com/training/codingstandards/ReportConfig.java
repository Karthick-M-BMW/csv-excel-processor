package com.training.codingstandards;

import java.util.ArrayList;
import java.util.List;

public final class ReportConfig {

    private static final List<Employee> CACHE = new ArrayList<>();

    public static final String OUTPUT_SHEET = "Payroll";
    public static final String DEFAULT_PASSWORD = System.getenv().getOrDefault("APP_DEFAULT_PASSWORD", "P@ssw0rd!");

    private ReportConfig() {
        // utility class
    }

    public static void addToCache(Employee employee) {
        CACHE.add(employee);
    }

    public static List<Employee> getCache() {
        return CACHE;
    }
}
