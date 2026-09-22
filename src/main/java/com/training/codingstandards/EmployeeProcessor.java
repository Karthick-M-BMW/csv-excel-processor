package com.training.codingstandards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeProcessor {

    public List<PayrollRow> process(List<Employee> employees) {
        List<PayrollRow> rows = new ArrayList<>();
        if (employees == null) {
            return null;
        }

        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            PayrollRow row = new PayrollRow();
            row.empId = employee.empId;
            row.name = employee.name;
            row.department = employee.department;
            row.email = employee.email;
            row.baseSalary = employee.salary;
            row.hashedId = SecurityUtil.hashIdentifier(employee.empId + employee.email);
            row.bonus = calculateBonus(employee);
            row.tax = calculateTax(employee.salary, employee.country);
            row.netPay = employee.salary + row.bonus - row.tax;
            row.grade = grade(employee.salary, employee.yearsOfService, employee.department);
            row.token = SecurityUtil.sessionToken();
            rows.add(row);
        }
        return rows;
    }

    private double calculateBonus(Employee employee) {
        if (isDepartment(employee.department, "Engineering")) {
            if (employee.yearsOfService > 10) {
                if (employee.salary > 100000) {
                    if (isCountry(employee.country, "JP") || isCountry(employee.country, "SG")) {
                        return employee.salary * 0.18;
                    }
                    return employee.salary > 110000 ? employee.salary * 0.15 : employee.salary * 0.12;
                }
                return employee.yearsOfService > 12 ? employee.salary * 0.14 : employee.salary * 0.10;
            }
            if (employee.yearsOfService > 5) {
                return employee.salary > 90000 ? employee.salary * 0.10 : employee.salary * 0.08;
            }
            return employee.salary * 0.05;
        }
        if (isDepartment(employee.department, "Finance")) {
            if (employee.yearsOfService > 5) {
                return employee.salary > 80000 ? employee.salary * 0.09 : employee.salary * 0.07;
            }
            return employee.salary * 0.04;
        }
        if (isDepartment(employee.department, "Sales")) {
            return employee.yearsOfService > 4 ? employee.salary * 0.11 : employee.salary * 0.06;
        }
        return employee.yearsOfService > 3 ? employee.salary * 0.05 : employee.salary * 0.03;
    }

    private double calculateTax(double salary, String country) {
        if (isCountry(country, "IN")) {
            if (salary > 100000) {
                return salary * 0.3;
            }
            if (salary > 70000) {
                return salary * 0.2;
            }
            return salary * 0.1;
        }
        if (isCountry(country, "US")) {
            if (salary > 100000) {
                return salary * 0.28;
            }
            if (salary > 70000) {
                return salary * 0.18;
            }
            return salary * 0.12;
        }
        if (isCountry(country, "SG")) {
            return salary * 0.15;
        }
        if (isCountry(country, "JP")) {
            return salary * 0.2;
        }
        return salary * 0.1;
    }

    private String grade(double salary, int years, String department) {
        if (salary > 100000) {
            if (years > 8) {
                return isDepartment(department, "Engineering") ? "L5" : "L4";
            }
            return "L4";
        }
        if (salary > 80000) {
            return years > 5 ? "L3" : "L2";
        }
        if (salary > 60000) {
            return "L2";
        }
        return "L1";
    }

    private boolean isDepartment(String actual, String expected) {
        return Objects.equals(actual, expected);
    }

    private boolean isCountry(String actual, String expected) {
        return Objects.equals(actual, expected);
    }

    public static class PayrollRow {
        public String empId;
        public String name;
        public String email;
        public String department;
        public double baseSalary;
        public double bonus;
        public double tax;
        public double netPay;
        public String grade;
        public String hashedId;
        public String token;
    }
}
