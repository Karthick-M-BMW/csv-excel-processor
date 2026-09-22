package com.training.codingstandards;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class DatabaseHelper {

    private static final Logger LOGGER = Logger.getLogger(DatabaseHelper.class.getName());
    private static final String URL = System.getenv().getOrDefault("APP_DB_URL", "jdbc:h2:mem:training-demo");
    private static final String USER = System.getenv().getOrDefault("APP_DB_USER", "sa");
    private static final String PASSWORD = System.getenv().getOrDefault("APP_DB_PASSWORD", "");

    public Employee findEmployee(String empId) {
        String sql = "SELECT emp_id, name FROM employees WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            LOGGER.info(() -> "Querying employee by ID: " + empId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.empId = rs.getString("emp_id");
                    employee.name = rs.getString("name");
                    return employee;
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Database lookup failed: " + e.getMessage());
        }
        return null;
    }

    public void auditExport(String userInputPath) {
        if (userInputPath == null || userInputPath.isBlank()) {
            return;
        }
        try {
            Path path = Paths.get(userInputPath).normalize();
            if (Files.exists(path)) {
                LOGGER.info(() -> "Audit path exists: " + path);
            }
        } catch (Exception e) {
            LOGGER.warning("Invalid export path: " + userInputPath);
        }
    }
}
