package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CsvEmployeeReader {

    private static final Logger LOGGER = Logger.getLogger(CsvEmployeeReader.class.getName());

    public List<Employee> read(String csvPath) {
        List<Employee> employees = new ArrayList<>();
        try (InputStream inputStream = csvPath == null
                ? CsvEmployeeReader.class.getResourceAsStream("/employees.csv")
                : new FileInputStream(csvPath);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
            if (inputStream == null) {
                throw new IOException("Employee CSV resource not found");
            }
            for (CSVRecord record : parser) {
                Employee employee = new Employee();
                employee.empId = record.get("empId");
                employee.name = record.get("name");
                employee.email = record.get("email");
                employee.department = record.get("department");
                employee.salary = Double.parseDouble(record.get("salary"));
                employee.yearsOfService = Integer.parseInt(record.get("yearsOfService"));
                employee.country = record.get("country");
                employee.managerEmail = record.get("managerEmail");
                employees.add(employee);
                ReportConfig.addToCache(employee);
                LOGGER.info(() -> "Loaded employee " + employee.name + " email=" + employee.email);
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to load CSV employees: " + e.getMessage());
        }
        return employees;
    }
}
