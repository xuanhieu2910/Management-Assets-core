package com.example.csvccdshustbe.utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Objects;

public class ExcelUtil {


    public static Object convertValue(Cell cell, CellType cellType) {
        if(Objects.isNull(cell) || cell.getCellType().equals(CellType.BLANK)) {
            return null;
        }
        cell.setCellType(cellType);
        Object object;
        try {
            object = switch (cellType) {
                case STRING -> cell.getStringCellValue().trim().replaceAll("'", "");
                case NUMERIC -> Integer.valueOf(cell.getStringCellValue().trim().replaceAll("'", ""));
                default -> null;
            };
            return object;
        } catch (Exception ex) {
            return null;
        }
    }


    public static Integer validateRowLastNull(XSSFSheet xssfSheet, int currentRowIndex) {
        Row row = xssfSheet.getRow(currentRowIndex);
        if(row != null) {
            return currentRowIndex;
        }
        int countEmptyRow = 0;
        while(countEmptyRow <= 5 && isRowBlank(xssfSheet.getRow(currentRowIndex))) {
            countEmptyRow++;
        }

        return 0;
    }

    public static boolean isRowBlank(Row row) {
        for (int indexCell = 0; indexCell < 5; indexCell++) {
            Cell cell = row.getCell(indexCell);
            if (!validateFiledBlankOrNull(cell)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateFiledBlankOrNull(Cell cellValue) {
        return cellValue == null || cellValue.getCellType() == CellType.BLANK ;
    }
}
