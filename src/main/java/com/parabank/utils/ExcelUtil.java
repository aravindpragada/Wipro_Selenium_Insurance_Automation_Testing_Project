package com.parabank.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    
    public static Object[][] readSheetByHeaders(String path, String sheetName, String[] headers) {
        try (InputStream in = new FileInputStream(path);
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + path);
            }

            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row missing in sheet: " + sheetName);
            }

            Map<String, Integer> headerIndex = new HashMap<>();
            for (Cell c : headerRow) {
                String hv = formatter.formatCellValue(c).trim().toLowerCase();
                if (!hv.isEmpty()) headerIndex.put(hv, c.getColumnIndex());
            }

            int rows = sheet.getPhysicalNumberOfRows();
            if (rows <= 1) return new Object[0][headers.length];

            Object[][] data = new Object[rows - 1][headers.length];

            for (int r = 1; r < rows; r++) {
                Row row = sheet.getRow(r);
                for (int h = 0; h < headers.length; h++) {
                    String headerKey = headers[h].trim().toLowerCase();
                    if (headerIndex.containsKey(headerKey)) {
                        int colIdx = headerIndex.get(headerKey);
                        if (row == null) {
                            data[r - 1][h] = "";
                        } else {
                            Cell cell = row.getCell(colIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            data[r - 1][h] = formatter.formatCellValue(cell).trim();
                        }
                    } else {
                        data[r - 1][h] = "";
                    }
                }
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[][] readSheet(String path, String sheetName) {
        try (InputStream in = new FileInputStream(path); Workbook wb = new XSSFWorkbook(in)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + path);

            DataFormatter formatter = new DataFormatter();
            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows - 1][cols];
            for (int i = 1; i < rows; i++) {
                Row r = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell c = (r == null) ? null : r.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = (c == null) ? "" : formatter.formatCellValue(c).trim();
                }
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
