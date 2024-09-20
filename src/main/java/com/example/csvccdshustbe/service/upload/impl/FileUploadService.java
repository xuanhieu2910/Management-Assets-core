package com.example.csvccdshustbe.service.upload.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
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
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    AssetCategoriesService assetCategoriesService;



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
    public static void main(String[] args) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");



        // Dữ liệu cho các loại sản phẩm (dropdown chính)
        String[] categories = {"Electronics", "Furniture"};
        String[] electronicsProducts = {"A1","A2","A3","A4","A5","A6"};
        String[] furnitureProducts = {"B1","B2","B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10"};

        // Tạo dữ liệu cho cột ẩn dùng làm dữ liệu nguồn cho dropdown phụ thuộc
        Sheet hiddenSheet = workbook.createSheet("HiddenData");
        createHiddenData(hiddenSheet, electronicsProducts, furnitureProducts);

        // Tạo dropdown chính (Loại Sản Phẩm)
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(categories);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(1, 1, 0, 0); // Cell A2
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        sheet.addValidationData(categoryValidation);

        // Tạo dropdown phụ thuộc (Sản Phẩm)
        String formula = "INDIRECT($A2)"; // Sử dụng INDIRECT để lấy giá trị phụ thuộc vào A2
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(1, 1, 1, 1); // Cell B2
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        sheet.addValidationData(productValidation);

//         Ẩn sheet chứa dữ liệu ẩn
//        workbook.setSheetHidden(workbook.getSheetIndex("HiddenData"), true);

        // Lưu file Excel
        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\hieux\\Desktop\\Projects\\DependentDropdownExample.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    // Hàm tạo dữ liệu ẩn cho các danh sách dropdown phụ thuộc
    private static void createHiddenData(Sheet hiddenSheet, String[] electronicsProducts, String[] furnitureProducts) {
        // Ghi dữ liệu vào sheet ẩn
//        Row categoryRow = hiddenSheet.createRow(0);
//        categoryRow.createCell(0).setCellValue("Electronics");
//        categoryRow.createCell(1).setCellValue("Furniture");

        int tmpEl = 1;
        int tmpFur = 1;
        for (int i = 0; i < electronicsProducts.length; i++) {
            Row rowDataElec = hiddenSheet.createRow(tmpEl);
            rowDataElec.createCell(0).setCellValue(electronicsProducts[i]);
            ++tmpEl;
        }
        for (int i = 0; i < furnitureProducts.length; i++) {
            Row rowDataElec;
            if (hiddenSheet.getRow(tmpFur) == null) {
                rowDataElec = hiddenSheet.createRow(tmpFur);
            } else {
                rowDataElec = hiddenSheet.getRow(tmpFur);
            }
            rowDataElec.createCell(1).setCellValue(furnitureProducts[i]);
            CellReference cellReference = new CellReference(rowDataElec.getCell(1));
            System.out.println(cellReference.formatAsString());
            ++tmpFur;
        }

        // Tạo các range tên để sử dụng với INDIRECT
        Name electronicsRange = hiddenSheet.getWorkbook().createName();
        electronicsRange.setNameName("Electronics");
        electronicsRange.setRefersToFormula("HiddenData!$A$2:$A$" + tmpEl);

        Name furnitureRange = hiddenSheet.getWorkbook().createName();
        furnitureRange.setNameName("Furniture");
        furnitureRange.setRefersToFormula("HiddenData!$B$2:$B$" + tmpFur);
    }

    @Override
    public Resource downLoadFileImportAsset() throws IOException {
        Map<String, List<FindAllAssetCategoriesByCodeAndVisibleDto>> mapAssetCategory =
                assetCategoriesService.findAllAssetCategoriesVisibleResponseToDownload();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ImportAsset");
        createAssetCategories(workbook.createSheet("AssetCategories"), mapAssetCategory);
        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\hieux\\Desktop\\Projects\\DependentDropdownExample.xlsx")) {
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

    private void createAssetCategories(Sheet sheetAssetCategories, Map<String, List<FindAllAssetCategoriesByCodeAndVisibleDto>> mapAssetCategory) {
        Iterator<String> keywords = mapAssetCategory.keySet().iterator();
        int index = 0;
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataAssetCategory(sheetAssetCategories,mapAssetCategory.get(keyword), index, keyword);
            ++index;
        }
    }
    private void filledDataAssetCategory(Sheet sheetAssetCategories,
                                         List<FindAllAssetCategoriesByCodeAndVisibleDto> dtos,
                                         int index, String keywords) {
        Row row = null;
        for (int i = INDEX_START_FILLED_DATA; i < dtos.size(); i++) {
            if (sheetAssetCategories.getRow(i) == null) {
                row = sheetAssetCategories.createRow(i);
            } else {
                row = sheetAssetCategories.getRow(i);
            }
            String valueCell = dtos.get(i).getIdAssetCategory() + "." + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);

        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().substring(0, 1);
            Name electronicsRange = sheetAssetCategories.getWorkbook().createName();
            electronicsRange.setNameName(keywords);
            electronicsRange.setRefersToFormula("AssetCategories!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1) + ":$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1 + dtos.size()));
        }
    }
}
