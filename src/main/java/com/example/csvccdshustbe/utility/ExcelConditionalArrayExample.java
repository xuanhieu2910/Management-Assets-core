package com.example.csvccdshustbe.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelConditionalArrayExample {
//    public static void main(String[] args) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet mainSheet = workbook.createSheet("Main Sheet");
//        Sheet hiddenSheet = workbook.createSheet("Hidden Sheet");
//
//        // Ẩn sheet chứa dữ liệu phụ trợ
//        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);
//
//        // Mảng C chứa các nhóm chính
//        String[] arrayC = {"Group1", "Group2"};
//        String[] arrayA1 = {"Option A1", "Option A2"}; // Dữ liệu cho Group1
//        String[] arrayA2 = {"Option A3", "Option A4"}; // Dữ liệu cho Group2
//        String[] arrayB1 = {"Option B1", "Option B2"}; // Dữ liệu cho Group1
//        String[] arrayB2 = {"Option B3", "Option B4"}; // Dữ liệu cho Group2
//
//        // Ghi dữ liệu vào sheet phụ trợ (Hidden Sheet)
//        // Ghi dữ liệu cho Group1
//        Row row1 = hiddenSheet.createRow(0);
//        for (int i = 0; i < arrayA1.length; i++) {
//            row1.createCell(i).setCellValue(arrayA1[i]);
//        }
//        Row row2 = hiddenSheet.createRow(1);
//        for (int i = 0; i < arrayB1.length; i++) {
//            row2.createCell(i).setCellValue(arrayB1[i]);
//        }
//
//        // Ghi dữ liệu cho Group2
//        Row row3 = hiddenSheet.createRow(2);
//        for (int i = 0; i < arrayA2.length; i++) {
//            row3.createCell(i).setCellValue(arrayA2[i]);
//        }
//        Row row4 = hiddenSheet.createRow(3);
//        for (int i = 0; i < arrayB2.length; i++) {
//            row4.createCell(i).setCellValue(arrayB2[i]);
//        }
//
//        // Ghi mảng C vào cột A của sheet chính
//        for (int i = 0; i < arrayC.length; i++) {
//            Row mainRow = mainSheet.createRow(i);
//            Cell cell = mainRow.createCell(0); // Cột 1 (cột A trong Excel)
//            cell.setCellValue(arrayC[i]);
//        }
//
//        // Tạo Data Validation cho cột A (mảng C)
//        DataValidationHelper validationHelper = mainSheet.getDataValidationHelper();
//        CellRangeAddressList addressListC = new CellRangeAddressList(0, arrayC.length - 1, 0, 0); // Cột A
//        DataValidationConstraint constraintC = validationHelper.createExplicitListConstraint(arrayC);
//        DataValidation validationC = validationHelper.createValidation(constraintC, addressListC);
//        mainSheet.addValidationData(validationC);
//
//        // Tạo Named Ranges cho các nhóm
//        Name namedRangeA1 = workbook.createName();
//        namedRangeA1.setNameName("Group1A");
//        namedRangeA1.setRefersToFormula("'Hidden Sheet'!$A$1:$A$2"); // Group1 Options
//
//        Name namedRangeA2 = workbook.createName();
//        namedRangeA2.setNameName("Group2A");
//        namedRangeA2.setRefersToFormula("'Hidden Sheet'!$A$3:$A$4"); // Group2 Options
//
//        Name namedRangeB1 = workbook.createName();
//        namedRangeB1.setNameName("Group1B");
//        namedRangeB1.setRefersToFormula("'Hidden Sheet'!$B$1:$B$2"); // Group1 Options
//
//        Name namedRangeB2 = workbook.createName();
//        namedRangeB2.setNameName("Group2B");
//        namedRangeB2.setRefersToFormula("'Hidden Sheet'!$B$3:$B$4"); // Group2 Options
//
//        // Tạo Data Validation cho cột B và C (dropdown phụ thuộc dựa vào giá trị cột A)
//        for (int i = 0; i < arrayC.length; i++) {
//            String formulaA = "IF($A" + (i + 1) + "=\"Group1\", Group1A, Group2A)"; // Lựa chọn cho cột B
//            CellRangeAddressList addressListA = new CellRangeAddressList(i, i, 1, 1); // Cột B
//            DataValidationConstraint constraintA = validationHelper.createFormulaListConstraint(formulaA);
//            DataValidation validationA = validationHelper.createValidation(constraintA, addressListA);
//            mainSheet.addValidationData(validationA);
//
//            String formulaB = "IF($A" + (i + 1) + "=\"Group1\", Group1B, Group2B)"; // Lựa chọn cho cột C
//            CellRangeAddressList addressListB = new CellRangeAddressList(i, i, 2, 2); // Cột C
//            DataValidationConstraint constraintB = validationHelper.createFormulaListConstraint(formulaB);
//            DataValidation validationB = validationHelper.createValidation(constraintB, addressListB);
//            mainSheet.addValidationData(validationB);
//        }
//
//        // Ghi workbook ra file Excel
//        try (FileOutputStream outputStream = new FileOutputStream("DependentDropdownAlternative.xlsx")) {
//            workbook.write(outputStream);
//        }
//
//        workbook.close();
//        System.out.println("File Excel đã được tạo thành công với dependent dropdowns!");
//    }

    public static void main(String[] args) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet mainSheet = workbook.createSheet("Main Sheet");
        Sheet hiddenSheet = workbook.createSheet("Hidden Sheet");

        // Ẩn sheet chứa dữ liệu phụ trợ
        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);

        // Mảng C (nhiều giá trị)
        String[] arrayC = {"Group 1", "Group 2", "Group 3"};

        // Các mảng A1 và A2 cho từng nhóm
        String[][] arrayA1 = {
                {"Option A1-1", "Option A1-2"}, // Group 1
                {"Option A1-3", "Option A1-4"}, // Group 2
                {"Option A1-5", "Option A1-6"}  // Group 3
        };

        String[][] arrayA2 = {
                {"Option A2-1", "Option A2-2"}, // Group 1
                {"Option A2-3", "Option A2-4"}, // Group 2
                {"Option A2-5", "Option A2-6"}  // Group 3
        };

        // Ghi dữ liệu vào sheet phụ trợ (Hidden Sheet)
        for (int i = 0; i < arrayA1.length; i++) {
            Row rowA1 = hiddenSheet.createRow(i);
            for (int j = 0; j < arrayA1[i].length; j++) {
                rowA1.createCell(j).setCellValue(arrayA1[i][j]);
            }

            Row rowA2 = hiddenSheet.createRow(i + arrayA1.length); // Ghi dữ liệu cho A2 sau A1
            for (int j = 0; j < arrayA2[i].length; j++) {
                rowA2.createCell(j).setCellValue(arrayA2[i][j]);
            }
        }

        // Ghi mảng C vào cột A của sheet chính
        Row mainRow = mainSheet.createRow(0);
        for (int i = 0; i < arrayC.length; i++) {
            Cell cellC = mainRow.createCell(i); // Cột A
            cellC.setCellValue(arrayC[i]);
        }

        // Tạo Data Validation cho cột A
        DataValidationHelper validationHelper = mainSheet.getDataValidationHelper();
        CellRangeAddressList addressListC = new CellRangeAddressList(0, 0, 0, arrayC.length - 1); // Cột A
        DataValidationConstraint constraintC = validationHelper.createExplicitListConstraint(arrayC);
        DataValidation validationC = validationHelper.createValidation(constraintC, addressListC);
        mainSheet.addValidationData(validationC);

        // Tạo Named Ranges cho các nhóm A1 và A2
        for (int i = 0; i < arrayC.length; i++) {
            Name namedRangeA1 = workbook.createName();
            namedRangeA1.setNameName("Options_A1_Group" + (i + 1));
            namedRangeA1.setRefersToFormula("'Hidden Sheet'!$A$" + (i + 1) + ":$B$" + (i + 1));

            Name namedRangeA2 = workbook.createName();
            namedRangeA2.setNameName("Options_A2_Group" + (i + 1));
            namedRangeA2.setRefersToFormula("'Hidden Sheet'!$C$" + (i + 1) + ":$D$" + (i + 1));
        }

        // Tạo Data Validation cho cột B và C
        for (int i = 0; i < arrayC.length; i++) {
            // Dropdown cho cột B
            String formulaB = "INDIRECT(\"Options_A1_Group" + (i + 1) + "\")";
            CellRangeAddressList addressListB = new CellRangeAddressList(0, 0, i, i); // Cột B
            DataValidationConstraint constraintB = validationHelper.createFormulaListConstraint(formulaB);
            DataValidation validationB = validationHelper.createValidation(constraintB, addressListB);
            mainSheet.addValidationData(validationB);

            // Dropdown cho cột C
            String formulaC2 = "INDIRECT(\"Options_A2_Group" + (i + 1) + "\")";
            CellRangeAddressList addressListC2 = new CellRangeAddressList(0, 0, i, i); // Cột C
            DataValidationConstraint constraintC2 = validationHelper.createFormulaListConstraint(formulaC2);
            DataValidation validationC2 = validationHelper.createValidation(constraintC2, addressListC2);
            mainSheet.addValidationData(validationC2);
        }

        // Ghi workbook ra file Excel
        try (FileOutputStream outputStream = new FileOutputStream("DropdownWithMultipleGroups.xlsx")) {
            workbook.write(outputStream);
        }

        workbook.close();
        System.out.println("File Excel đã được tạo thành công với dropdown phụ thuộc cho nhiều giá trị trong mảng C!");
    }
}
