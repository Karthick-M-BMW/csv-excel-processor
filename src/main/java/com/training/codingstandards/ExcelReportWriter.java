package com.training.codingstandards;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ExcelReportWriter {

    private static final Logger LOGGER = Logger.getLogger(ExcelReportWriter.class.getName());

    public void write(List<EmployeeProcessor.PayrollRow> rows, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(outputPath)) {
            Sheet sheet = workbook.createSheet(ReportConfig.OUTPUT_SHEET);

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Employee Id");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Email");
            header.createCell(3).setCellValue("Department");
            header.createCell(4).setCellValue("Base Salary");
            header.createCell(5).setCellValue("Bonus");
            header.createCell(6).setCellValue("Tax");
            header.createCell(7).setCellValue("Net Pay");
            header.createCell(8).setCellValue("Grade");
            header.createCell(9).setCellValue("Hashed Id");
            header.createCell(10).setCellValue("Session Token");

            int rowIndex = 1;
            for (EmployeeProcessor.PayrollRow payrollRow : rows) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(payrollRow.empId);
                row.createCell(1).setCellValue(payrollRow.name);
                row.createCell(2).setCellValue(payrollRow.email);
                row.createCell(3).setCellValue(payrollRow.department);
                row.createCell(4).setCellValue(payrollRow.baseSalary);
                row.createCell(5).setCellValue(payrollRow.bonus);
                row.createCell(6).setCellValue(payrollRow.tax);
                row.createCell(7).setCellValue(payrollRow.netPay);
                row.createCell(8).setCellValue(payrollRow.grade);
                row.createCell(9).setCellValue(payrollRow.hashedId);
                row.createCell(10).setCellValue(payrollRow.token);
                rowIndex++;
            }

            workbook.write(out);
            LOGGER.info(() -> "Excel written to " + outputPath);
        } catch (IOException e) {
            LOGGER.warning("Failed to write Excel report: " + e.getMessage());
        }
    }
}
