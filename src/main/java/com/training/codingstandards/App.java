package com.training.codingstandards;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        String csvPath = null;
        String excelPath = "payroll-report.xlsx";

        if (args.length > 0) {
            csvPath = args[0];
        }
        if (args.length > 1) {
            excelPath = args[1];
        }

        LOGGER.info("CSV to Excel processor starting...");

        CsvEmployeeReader reader = new CsvEmployeeReader();
        List<Employee> employees = reader.read(csvPath);

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(employees);

        File out = new File(excelPath);
        ExcelReportWriter writer = new ExcelReportWriter();
        writer.write(rows, out.getAbsolutePath());

        final String finalExcelPath = excelPath;
        DatabaseHelper db = new DatabaseHelper();
        if (args.length > 2) {
            db.auditExport(args[2]);
            String lookupId = args.length > 3 ? args[3] : employees.get(0).empId;
            Employee lookedUp = db.findEmployee(lookupId);
            if (lookedUp != null) {
                String lookupName = lookedUp.name;
                LOGGER.info(() -> "Lookup result: " + lookupName);
            }
        }

        final int processedCount = rows == null ? 0 : rows.size();
        LOGGER.info(() -> "Processed " + processedCount + " employees into " + finalExcelPath);
    }
}
