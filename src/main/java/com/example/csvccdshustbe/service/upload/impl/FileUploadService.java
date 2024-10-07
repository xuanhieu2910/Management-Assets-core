package com.example.csvccdshustbe.service.upload.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesToDownloadDto;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.documentAttack.FindAllDocumentAttackDto;
import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.dto.unit.FindAllUnitsDto;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.FileUtil;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressBase;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellRangeUtil;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Log4j2
@Service
public class FileUploadService implements FilesStorageService {


    public static final String FOLDER_AVATAR = "avatar";
    public static final String FOLDER_PAYMENT = "payment";
    private static final String SEPARATOR = File.separator;

    private static final String REMOVE_FILE_UNIX = "rm -rf";
    private static final String REMOVE_FILE_WIN = "del";
    private static final String CREATE_FOLDER = "mkdir";
    private static final String CREATE_FILE_UNIX = "touch";
    private static final String CREATE_FILE_WIN = "copy con";
    private static final String FILE_TEMPLATE_UP_ASSET = "Template_upload_asset";
    private static final Integer INDEX_START_FILLED_DATA = 1;
    private static final String NAME_SHEET_IMPORT_ASSET_CATEGORY = "ImportAsset";
    private static final String NAME_SHEET_DATA_ASSET_CATEGORY = "AssetCategories";
    private static final String NAME_SHEET_DATA_DEPARTMENT = "Department";
    private static final String NAME_SHEET_DATA_UNITS = "Units";
    private static final String NAME_SHEET_DATA_DOCUMENT_ATTACK = "DocumentAttacks";
    private static final String NAME_INDIRECT = "INDIRECT";
    private static final String VLOOKUP = "VLOOKUP";

    private static final String ERROR = "Error!";
    private static final String PROMPT = "Notes";
    private static final String[] PREFIX = {"category_","unit_", "original_", "location_", "documents_"};

    @Autowired
    AssetCategoriesService assetCategoriesService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UnitsService unitsService;
    @Autowired
    DocumentAttackService documentAttackService;


    @Override
    public  String saveAndReturnPathAsset(MultipartFile uploadedFile, String folderName) throws IOException, FileException {
        FileUtil.checkFileAsset(uploadedFile);
        return saveFile(uploadedFile, folderName);
    }

    @Override
    public void deleteByPathFile(String pathFile) throws ValidateFiledException, IOException, InterruptedException {
        if (StringUtils.isBlank(pathFile)){
            throw new ValidateFiledException("validate data request");
        }
        pathFile = pathFile.replace(PropertiesUtil.getProperty("hust.csvc.static.location.static.files"),
                PropertiesUtil.getProperty("hust.csvc.static.location.upload"));
        executeDeleteCommand(pathFile);

    }

    public static void executeDeleteCommand(String command) throws InterruptedException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String osName = System.getProperty("os.name").toLowerCase();
        String[] cmdArray = null;
        if (osName.contains("win")) {
            command = REMOVE_FILE_WIN + " " + command;
            cmdArray = new String[]{"cmd.exe", "/c", command};
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            command = REMOVE_FILE_UNIX + " " + command;
            cmdArray = new String[]{"/bin/bash", "-c", command};
        }
        processBuilder.command(cmdArray);
        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            log.info("\nExited with code : " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Can't delete file");
        } catch (InterruptedException e) {
            throw new InterruptedException("Can't delete file");
        }
    }

    public static void executeCreateFolderCommand(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String osName = System.getProperty("os.name").toLowerCase();
        String[] cmdArray = null;
        if (osName.contains("win")) {
            command = CREATE_FOLDER + " " + command;
            cmdArray = new String[]{"cmd.exe", "/c", command};
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            command = CREATE_FOLDER + " " + command;
            cmdArray = new String[]{"/bin/bash", "-c", command};
        }
        processBuilder.command(cmdArray);
        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            log.info("\nExited with code : " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void executeCreateFileCommand(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String osName = System.getProperty("os.name").toLowerCase();
        String[] cmdArray = null;
        if (osName.contains("win")) {
            command = CREATE_FILE_WIN + " " + command;
            cmdArray = new String[]{"cmd.exe", "/c", command};
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            command = CREATE_FILE_UNIX + " " + command;
            cmdArray = new String[]{"/bin/bash", "-c", command};
        }
        processBuilder.command(cmdArray);
        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            log.info("\nExited with code : " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String saveFile(MultipartFile uploadedFile, String folderName) throws IOException {
        return save(uploadedFile, folderName);
    }

    private String save(MultipartFile multipartFile,  String folderName) throws IOException {
        String folderSave = PropertiesUtil.getProperty("hust.csvc.static.location.upload");
        String fileId = generateFileId();
        String folder = buildFolderUpload(folderName);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        String namePathFileResponse = folder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        File file = new File(folderSave + namePathFileResponse);
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.touch(file);
            multipartFile.transferTo(file);
        } catch (IOException e){
            log.error("Can't not save file, file error!", e);
            throw new IOException("Can't not save file, file error!");
        }
        return namePathFileResponse;
    }

    private static String generateFileId() {
        return DateUtil.getCurrentDateStr() + RandomStringUtils.randomAlphanumeric(16);
    }

    private static String buildFolderUpload(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        return  SEPARATOR + folderName + SEPARATOR + todayFolder;
    }

    @Override
    public Resource downLoadFileImportAsset() throws IOException {
        String fileExcel = "C:\\Users\\hieux\\Desktop\\Projects\\src\\main\\resources\\static\\Template_import_asset.xlsx";
        FileInputStream file = new FileInputStream(new File(fileExcel));

        Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategory =
                assetCategoriesService.findAllAssetCategoriesVisibleResponseToDownload();
        Map<String,List<FindAllLocationDto>> dataDepartment =
                departmentService.findAllDepartmentLocationVisibleToDownload();
        Map<String,List<FindAllUnitsDto>> dataUnits = unitsService.findAllUnitsToDownload();
        Map<String,List<FindAllDocumentAttackDto>> dataDocumentAttack =
                documentAttackService.findAllDocumentAttackToDownload();


        Workbook workbook = new XSSFWorkbook(file);
        createAssetCategoriesImport(workbook, mapAssetCategory);
        createAssetDepartmentImport(workbook, dataDepartment);
        createAssetUnits(workbook, dataUnits);
        createDocumentAttack(workbook, dataDocumentAttack);
        String filePathOutput = "C:\\Users\\hieux\\Desktop\\Projects\\Template_import_asset.xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePathOutput)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
        //Common
        //Modules
        //Original
        //Declare

        //Lấy danh mục tài san picked
        //Lay danh sach tai san theo picked
        //Lay danh sach department
        //Lay danh sach location theo department
        //Lay danh sach don vi theo picked
        // Document
        // Project
        //
        return null;
    }

    private void createDocumentAttack(Workbook workbook, Map<String, List<FindAllDocumentAttackDto>> dataDocumentAttack) {
        Sheet sheetDocumentAttack = workbook.createSheet(NAME_SHEET_DATA_DOCUMENT_ATTACK);
        Iterator<String> keywords = dataDocumentAttack.keySet().iterator();
        int index = 0;
        String[] documents = new String[dataDocumentAttack.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataDocumentAttack(sheetDocumentAttack,dataDocumentAttack.get(keyword), index, keyword);
            documents[index] = keyword;
            ++index;
        }

        int indexFirstRow = 3;
        int limitAmountRow = 2000;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[4] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 6,6);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
    }

    private void filledDataDocumentAttack(Sheet sheetDocumentAttack,
                                          List<FindAllDocumentAttackDto> dtos, int index,
                                          String keyword) {
        Row row = null;
        for (int i = INDEX_START_FILLED_DATA; i < dtos.size(); i++) {
            if (sheetDocumentAttack.getRow(i) == null) {
                row = sheetDocumentAttack.createRow(i);
            } else {
                row = sheetDocumentAttack.getRow(i);
            }
            String valueCell = dtos.get(i).getIdDocumentAttack() + "." + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);

        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().substring(0, 1);
            Name electronicsRange = sheetDocumentAttack.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[4] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DOCUMENT_ATTACK
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + dtos.size());
        }
    }

    private void createAssetUnits(Workbook workbook, Map<String, List<FindAllUnitsDto>> dataUnits) {
        Sheet sheetUnit = workbook.createSheet(NAME_SHEET_DATA_UNITS);
        Iterator<String> keywords = dataUnits.keySet().iterator();
        int index = 0;
        String[] units = new String[dataUnits.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataUnits(sheetUnit,dataUnits.get(keyword), index, keyword);
            units[index] = keyword;
            ++index;
        }

        int indexFirstRow = 3;
        int limitAmountRow = 2000;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[1] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 5,5);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
    }

    private void filledDataUnits(Sheet sheetUnit, List<FindAllUnitsDto> dtos, int index, String keyword) {
        Row row = null;
        for (int i = INDEX_START_FILLED_DATA; i < dtos.size(); i++) {
            if (sheetUnit.getRow(i) == null) {
                row = sheetUnit.createRow(i);
            } else {
                row = sheetUnit.getRow(i);
            }
            String valueCell = dtos.get(i).getIdUnit() + "." + dtos.get(i).getNameUnit();
            row.createCell(index).setCellValue(valueCell);

        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().substring(0, 1);
            Name electronicsRange = sheetUnit.getWorkbook().getName(keyword);
            if (electronicsRange == null){
                electronicsRange = sheetUnit.getWorkbook().createName();
                electronicsRange.setNameName(PREFIX[1] + keyword);
            }
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_UNITS
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + dtos.size());
        }
    }

    private void createAssetDepartmentImport(Workbook workbook, Map<String,List<FindAllLocationDto>> dataDepartment) {
        Sheet sheetDepartment = workbook.createSheet(NAME_SHEET_DATA_DEPARTMENT);
        Iterator<String> keywords = dataDepartment.keySet().iterator();
        int index = 0;
        String[] departments = new String[dataDepartment.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataDepartment(sheetDepartment,dataDepartment.get(keyword), index, keyword);
            departments[index] = keyword;
            ++index;
        }

        int indexFirstRow = 3;
        int limitAmountRow = 2000;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(departments);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 3, 3);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

        String formula = NAME_INDIRECT + "(\"" + PREFIX[3] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 4,4);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
    }

    private void filledDataDepartment(Sheet sheetDepartment, List<FindAllLocationDto> dtos, int index, String keyword) {
        Row row = null;
        for (int i = INDEX_START_FILLED_DATA; i < dtos.size(); i++) {
            if (sheetDepartment.getRow(i) == null) {
                row = sheetDepartment.createRow(i);
            } else {
                row = sheetDepartment.getRow(i);
            }
            String valueCell = dtos.get(i).getIdLocation() + "." + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);

        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().substring(0, 1);
            Name electronicsRange = sheetDepartment.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[3] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DEPARTMENT
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + dtos.size());
        }
    }

    private void createAssetCategoriesImport(Workbook workbook, Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategory) {
        Sheet sheetAssetCategories = workbook.createSheet(NAME_SHEET_DATA_ASSET_CATEGORY);
        Iterator<String> keywords = mapAssetCategory.keySet().iterator();
        int index = 0;
        int indexFilledData = 0;
        String[] assetCategories = new String[mapAssetCategory.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            indexFilledData= filledDataAssetCategory(sheetAssetCategories,mapAssetCategory.get(keyword), indexFilledData, keyword);
            assetCategories[index] = keyword;
            ++index;
            ++indexFilledData;
        }

        int indexFirstRow = 3;
        int limitAmountRow = 2000;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(assetCategories);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 0, 0);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

        String formula = NAME_INDIRECT + "(\"" + PREFIX[0] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(indexFirstRow, limitAmountRow, 1,1);
        DataValidation subCategoryValidation = dvHelper.createValidation(productConstraint, productAddressList);
        subCategoryValidation.setShowErrorBox(true);
        subCategoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        subCategoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        subCategoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        subCategoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(subCategoryValidation);

        // SET DEPRECIATION
        for (int rowIndex = indexFirstRow; rowIndex <= limitAmountRow; rowIndex++) {
            Row row = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getRow(rowIndex);
            if (row == null) {
                row = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).createRow(rowIndex); // Create row if it doesn't exist
            }
            Cell cellMinDepreciation = row.createCell(135);
            Cell cellMaxDepreciation = row.createCell(136);
            String formulaMinDepreciation = "IF($B4=\"\",\"\"," + VLOOKUP + "($B4," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$C,2,0))";
            String formulaMaxDepreciation = "IF($B4=\"\",\"\"," + VLOOKUP + "($B4," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$C,3,0))";
            cellMinDepreciation.setCellFormula(formulaMinDepreciation);
            cellMaxDepreciation.setCellFormula(formulaMaxDepreciation);

        }
//        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_ASSET_CATEGORY), true);
    }
    private Integer filledDataAssetCategory(Sheet sheetAssetCategories,
                                         List<FindAllAssetCategoriesToDownloadDto> dtos,
                                         int index, String keywords) {
        Row row = null;
        int indexStart = 0;
        int dtosLength = index + dtos.size();
        for (int i = index; i < dtosLength; i++) {
            if (sheetAssetCategories.getRow(i) == null) {
                row = sheetAssetCategories.createRow(i);
            } else {
                row = sheetAssetCategories.getRow(i);
            }
            String valueCell = dtos.get(indexStart).getIdAssetCategory() + "." + dtos.get(indexStart).getName();
            String minimumTimeDepreciation = dtos.get(indexStart).getMinimumTimeDepreciation();
            String maximumTimeDepreciation = dtos.get(indexStart).getMaximumTimeDepreciation();
            row.createCell(0).setCellValue(valueCell);
            row.createCell(1).setCellValue(minimumTimeDepreciation);
            row.createCell(2).setCellValue(maximumTimeDepreciation);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(0));
            String prefix = cellReference.formatAsString().substring(0, 1);
            Name electronicsRange = sheetAssetCategories.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[0] + keywords);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_ASSET_CATEGORY
                    + "!$" + prefix + "$" + (index + 1)
                    + ":$" + prefix + "$" + dtosLength);
        }
        return dtosLength;
    }
}
