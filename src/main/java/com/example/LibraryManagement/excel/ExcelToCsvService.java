package com.example.LibraryManagement.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
@Service
public class ExcelToCsvService {

    public void convertToCsv(File excelFile, File csvOutputFile) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(excelFile);
                Workbook workbook = new XSSFWorkbook(fis);
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvOutputFile))
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cells = row.cellIterator();
                StringBuilder sb = new StringBuilder();

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    String value = getCellValueAsString(cell);
                    sb.append(value);

                    if (cells.hasNext()) {
                        sb.append(",");
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public static void main(String[] args) throws IOException {
        ExcelToCsvService converter = new ExcelToCsvService();
        File excel = new File("src/main/resources/sample.xlsx");
        File csv = new File("src/main/resources/output.csv");
        converter.convertToCsv(excel, csv);
    }

}
