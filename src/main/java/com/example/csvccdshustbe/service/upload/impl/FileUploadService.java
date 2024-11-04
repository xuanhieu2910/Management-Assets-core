package com.example.csvccdshustbe.service.upload.impl;

import com.example.csvccdshustbe.dto.asset.FindAllGroundAssetDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesToDownloadDto;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.dto.districts.DistrictsDto;
import com.example.csvccdshustbe.dto.documentAttack.FindAllDocumentAttackDto;
import com.example.csvccdshustbe.dto.goalsUseGround.FindAllGoalsUseGroundDto;
import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup.MedicineGroupDetailsDto;
import com.example.csvccdshustbe.dto.modules.medicineModules.medicineType.MedicineTypeDetailsDto;
import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.dto.positionName.FindAllPositionNameDto;
import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.dto.provinces.ProvincesDto;
import com.example.csvccdshustbe.dto.typeUse.FindAllTypeUseDto;
import com.example.csvccdshustbe.dto.unit.FindAllUnitsDto;
import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.dto.wards.WardsDto;
import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.districts.DistrictsService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.goalsUseGround.GoalsUseGroundService;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
import com.example.csvccdshustbe.service.original.OriginalService;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
import com.example.csvccdshustbe.service.positionName.PositionNameService;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.service.province.ProvinceService;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.wards.WardsService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.FileUtil;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import com.example.csvccdshustbe.utility.ValueUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class FileUploadService implements FilesStorageService {


    public static final String FOLDER_SAMPLE_EXCEL_IMPORT = "sample_import_asset";
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
    private static final String NAME_SHEET_DATA_LOCATION = "Location";
    private static final String NAME_SHEET_DATA_UNITS = "Units";
    private static final String NAME_SHEET_DATA_DOCUMENT_ATTACK = "DocumentAttacks";
    private static final String NAME_SHEET_DATA_PROJECTS = "Projects";
    private static final String NAME_SHEET_DATA_PROVINCES = "Provinces";
    private static final String NAME_SHEET_DATA_DISTRICTS = "Districts";
    private static final String NAME_SHEET_DATA_WARDS = "Wards";
    private static final String NAME_SHEET_DATA_ASSET_DEPARTMENT = "AssetDepartment";
    private static final String NAME_SHEET_DATA_ORIGINAL = "Original";
    private static final String NAME_SHEET_DATA_COUNTRY_PRODUCER = "CountryProducer";
    private static final String NAME_SHEET_DATA_USER_USED = "UserUsed";
    private static final String NAME_SHEET_DATA_TYPE_USED = "TypeUsed";
    private static final String NAME_SHEET_DATA_ASSET_GROUND = "AssetGround";
    private static final String NAME_SHEET_DATA_POSITION_NAME = "PositionName";
    private static final String NAME_SHEET_DATA_MEDICINE_TYPE = "MedicineType";
    private static final String NAME_SHEET_DATA_MEDICINE_GROUP = "MedicineGroup";
    private static final String NAME_SHEET_DATA_GOALS_USE_GROUND = "GoalsUseGround";
    private static final String NAME_SHEET_EXPLAIN = "Huongdannhapthongtin";

    private static final String NAME_INDIRECT = "INDIRECT";
    private static final String VLOOKUP = "VLOOKUP";
    private static final Integer TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW  = 3;
    private static final Integer TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW  = 2000;
    private static final String ERROR = "Error!";
    private static final String PROMPT = "Notes";
    private static final String[] PREFIX = {"category_","unit_", "original_",
            "location_", "documents_", "provinces_", "districts_","wards_", "userused_"};

    @Autowired
    AssetCategoriesService assetCategoriesService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UnitsService unitsService;
    @Autowired
    DocumentAttackService documentAttackService;
    @Autowired
    ProjectsService projectsService;
    @Autowired
    ProvinceService provinceService;
    @Autowired
    DistrictsService districtsService;
    @Autowired
    WardsService wardsService;
    @Autowired
    OriginalService originalService;
    @Autowired
    CountryProducerService countryProducerService;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    TypeUseService typeUseService;
    @Autowired
    PositionNameService positionNameService;
    @Autowired
    MedicineTypeService medicineTypeService;
    @Autowired
    MedicineGroupService medicineGroupService;
    @Autowired
    GoalsUseGroundService goalsUseGroundService;
    @Autowired
    OriginalOfFormationService originalOfFormationService;

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
    public String downLoadFileImportAsset() throws IOException {
//        String fileExcel = PropertiesUtil.getProperty("hust.csvc.static.location.resources.static") + SEPARATOR
//                + "Sample_Excel_Import_Asset.xlsx";
        String fileExcel = "D:\\CompanyBk\\CSVC\\csvc-hust\\src\\main\\resources\\static\\Sample_Excel_Import_Asset.xlsx";
        FileInputStream file = new FileInputStream(new File(fileExcel));

        Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategory =
                assetCategoriesService.findAllAssetCategoriesVisibleResponseToDownload();
        Map<String,List<FindAllLocationDto>> dataDepartment =
                departmentService.findAllDepartmentLocationVisibleToDownload();
        Map<String,List<FindAllUnitsDto>> dataUnits = unitsService.findAllUnitsToDownload();
        Map<String,List<FindAllDocumentAttackDto>> dataDocumentAttack =
                documentAttackService.findAllDocumentAttackToDownload();
        List<FindAllProjectsDto> dataProjects = projectsService.findAllProjectToDownload();
        List<ProvincesDto> dataProvinces = provinceService.findAllProvinceToDownload();
        Map<String, List<DistrictsDto>> dataDistrict = districtsService.findAllDistrictToDownload();
        Map<String, List<WardsDto>> dataWards = wardsService.findAllWardsToDownload();
        List<FindAllDepartmentSDto> dataAssetDepartment = departmentService.findAllAssetDepartmentToDownload();
        Map<String, List<FindAllOriginalDto>> dataOriginal = originalService.findAllOriginalToDownload();
        List<CountryProducer> dataCountryProducer = countryProducerService.findAllCountryProducerToDownload();
        Map<String, List<FindAllUserUsedDto>> dataUserUsed = csvcUserService.findAllUserUsedToDownload();
        List<FindAllGroundAssetDto> dataGroundAsset = assetRepository.findAllGroundAssetToDownload();
        List<FindAllTypeUseDto> dataTypeUse = typeUseService.findAllTypeUseToDownload();
        List<FindAllPositionNameDto> dataPositionName = positionNameService.findAllPositionNameToDownload();
        List<MedicineTypeDetailsDto> dataMedicineType = medicineTypeService.findAllMedicineTypeToDownload();
        List<MedicineGroupDetailsDto> dataMedicineGroup = medicineGroupService.findAllMedicineGroupToDownload();
        List<FindAllGoalsUseGroundDto> dataGoalsUseGround = goalsUseGroundService.findAllGoalsUseGroundToDownload();
        List<FindAllOriginalOfFormationDto> dataOriginalOfFormation = originalOfFormationService.findAllOriginalOfFormationToDownload();


        Workbook workbook = new XSSFWorkbook(file);
        createAssetCategoriesImport(workbook, mapAssetCategory);
        createAssetDepartmentImport(workbook, dataDepartment);
        createAssetUnits(workbook, dataUnits);
        createDocumentAttack(workbook, dataDepartment, dataDocumentAttack);
        createProjects(workbook, dataProjects);
        createProvinces(workbook, dataProvinces);
        createDistrict(workbook, dataDistrict);
        createWards(workbook, dataWards);
        createAssetDepartment(workbook, dataAssetDepartment);
        createOriginal(workbook, dataOriginal);
        createCountryProducer(workbook, dataCountryProducer);
        createUserUsed(workbook, dataUserUsed);
        createGroundAsset(workbook, dataGroundAsset);
        createTypeUse(workbook, dataTypeUse);
        createPositionName(workbook, dataPositionName);
        createDataMedicineType(workbook, dataMedicineType);
        createDataMedicineGroup(workbook, dataMedicineGroup);
        createDataGoalsUseGround(workbook, dataGoalsUseGround);
        createDataOriginalOfFormation(workbook, dataOriginalOfFormation);
        String root = PropertiesUtil.getProperty("hust.csvc.static.location.tomcat.webapp.csvcbe");
        String folder = root + SEPARATOR + FOLDER_SAMPLE_EXCEL_IMPORT + SEPARATOR + FileUtil.getFolderInfo();
        FileUtil.createFolder(folder);
        String fileFinal = folder + SEPARATOR + "Sample_Excel_Import_Asset_" + new Date().getTime() + ".xlsx";
        log.info("File final:" + fileFinal);
        File filePathOutput = FileUtil.createFileSampleAsset(fileFinal);
        String fileReturn = fileFinal.replace(root, PropertiesUtil.getProperty("hust.csvc.static.location.static.files"));
        log.info("File return: " + fileReturn);
        try (FileOutputStream fileOut = new FileOutputStream(filePathOutput)) {
            workbook.write(fileOut);
            workbook.close();
            return fileReturn;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String downLoadReportByPathFile(String pathFileReport) throws IOException {
        String file = PropertiesUtil.getProperty("hust.csvc.static.location.static.files") + pathFileReport;
        return file;
    }

    private void createDataOriginalOfFormation(Workbook workbook, List<FindAllOriginalOfFormationDto> dataOriginalOfFormation) {
        Sheet sheet = workbook.getSheet(NAME_SHEET_EXPLAIN);
        Row row = null;
        int indexCell = 13;
        int indexRowStart = 5;
        for (int i = 0; i< dataOriginalOfFormation.size(); i++){
            if (sheet.getRow(indexRowStart) == null) {
                row = sheet.createRow(indexRowStart);
            } else {
                row = sheet.getRow(indexRowStart);
            }
            String valueCell = dataOriginalOfFormation.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
            ++indexRowStart;
        }
    }

    private void createDataGoalsUseGround(Workbook workbook, List<FindAllGoalsUseGroundDto> dataGoalsUseGround) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_GOALS_USE_GROUND);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataGoalsUseGround.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataGoalsUseGround.get(i).getIdGoalsUseGround() + "_" + dataGoalsUseGround.get(i).getNameGoalsUseGround();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_GOALS_USE_GROUND + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_GOALS_USE_GROUND + "!$" + prefix + "$1:" + "$" + prefix + dataGoalsUseGround.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 119, 119);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_GOALS_USE_GROUND), true);
    }

    private void createDataMedicineGroup(Workbook workbook, List<MedicineGroupDetailsDto> dataMedicineGroup) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_MEDICINE_GROUP);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataMedicineGroup.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataMedicineGroup.get(i).getIdMedicineGroup() + "_" + dataMedicineGroup.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_MEDICINE_GROUP + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_MEDICINE_GROUP + "!$" + prefix + "$1:" + "$" + prefix + dataMedicineGroup.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 94, 94);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_MEDICINE_GROUP), true);
    }

    private void createDataMedicineType(Workbook workbook, List<MedicineTypeDetailsDto> dataMedicineType) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_MEDICINE_TYPE);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataMedicineType.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataMedicineType.get(i).getIdMedicineType() + "_" + dataMedicineType.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_MEDICINE_TYPE + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_MEDICINE_TYPE + "!$" + prefix + "$1:" + "$" + prefix + dataMedicineType.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 93, 93);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "PVui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_MEDICINE_TYPE), true);
    }

    private void createPositionName(Workbook workbook, List<FindAllPositionNameDto> dataPositionName) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_POSITION_NAME);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataPositionName.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataPositionName.get(i).getIdPositionName() + "_" + dataPositionName.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_POSITION_NAME + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_POSITION_NAME + "!$" + prefix + "$1:" + "$" + prefix + dataPositionName.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 63, 63);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 64, 64);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 82, 82);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_2);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_POSITION_NAME), true);
    }

    private void createTypeUse(Workbook workbook, List<FindAllTypeUseDto> dataTypeUse) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_TYPE_USED);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataTypeUse.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataTypeUse.get(i).getIdTypeUse() + "_" + dataTypeUse.get(i).getNameTypeUse();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_TYPE_USED + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_TYPE_USED + "!$" + prefix + "$1:" + "$" + prefix + dataTypeUse.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 22, 22);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);


            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 62, 62);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 81, 81);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_2);


            CellRangeAddressList categoryAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 85, 85);
            DataValidation categoryValidation_3 = dvHelper.createValidation(categoryConstraint, categoryAddressList_3);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_3);


            CellRangeAddressList categoryAddressList_4 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 92, 92);
            DataValidation categoryValidation_4 = dvHelper.createValidation(categoryConstraint, categoryAddressList_4);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_4);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_TYPE_USED), true);
    }

    private void createGroundAsset(Workbook workbook, List<FindAllGroundAssetDto> dataGroundAsset) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_ASSET_GROUND);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataGroundAsset.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataGroundAsset.get(i).getIdGroundAsset() + "_" + dataGroundAsset.get(i).getNameGroundAsset();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_ASSET_GROUND + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_ASSET_GROUND + "!$" + prefix + "$1:" + "$" + prefix + dataGroundAsset.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 28, 28);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 36, 36);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_ASSET_GROUND), true);
    }

    private void createUserUsed(Workbook workbook, Map<String, List<FindAllUserUsedDto>> dataUserUsed) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_USER_USED);
        Iterator<String> keywords = dataUserUsed.keySet().iterator();
        int index = 0;
        String[] data = new String[dataUserUsed.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataUserUsed(sheet,dataUserUsed.get(keyword), index, keyword);
            data[index] = keyword;
            ++index;
        }

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 21,21);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);


        DataValidationHelper dvHelper_1 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_1 = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint_1 = dvHelper_1.createFormulaListConstraint(formula_1);
        CellRangeAddressList productAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 61,61);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_1);

        DataValidationHelper dvHelper_2 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_2 = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint_2 = dvHelper_2.createFormulaListConstraint(formula_2);
        CellRangeAddressList productAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 80,80);
        DataValidation productValidation_2 = dvHelper.createValidation(productConstraint_2, productAddressList_2);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_2);


        DataValidationHelper dvHelper_3 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_3 = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint_3 = dvHelper_3.createFormulaListConstraint(formula_3);
        CellRangeAddressList productAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 91,91);
        DataValidation productValidation_3 = dvHelper.createValidation(productConstraint_3, productAddressList_3);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_3);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_USER_USED), true);
    }

    private void filledDataUserUsed(Sheet sheet, List<FindAllUserUsedDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStartFilledData = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheet.getRow(indexStartFilledData) == null) {
                row = sheet.createRow(indexStartFilledData);
            } else {
                row = sheet.getRow(indexStartFilledData);
            }
            String valueCell = dtos.get(i).getUserName() + "(" + dtos.get(i).getFullName() + ")";
            row.createCell(index).setCellValue(valueCell);
            ++indexStartFilledData;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_USER_USED + "!", "").
                    replaceAll("\\d","");
            Name electronicsRange = sheet.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[8] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_USER_USED
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStartFilledData);
        }
    }

    private void createCountryProducer(Workbook workbook, List<CountryProducer> dataCountryProducer) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_COUNTRY_PRODUCER);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataCountryProducer.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataCountryProducer.get(i).getIdCountryProducer() + "_" + dataCountryProducer.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_COUNTRY_PRODUCER + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_COUNTRY_PRODUCER + "!$" + prefix + "$1:" + "$" + prefix + dataCountryProducer.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 20, 20);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 41, 41);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 55, 55);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_2);

            CellRangeAddressList categoryAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 74, 74);
            DataValidation categoryValidation_3 = dvHelper.createValidation(categoryConstraint, categoryAddressList_3);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_3);

            CellRangeAddressList categoryAddressList_4 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 90, 90);
            DataValidation categoryValidation_4 = dvHelper.createValidation(categoryConstraint, categoryAddressList_4);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_4);

            CellRangeAddressList categoryAddressList_5 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 84, 84);
            DataValidation categoryValidation_5 = dvHelper.createValidation(categoryConstraint, categoryAddressList_5);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_5);
            workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_COUNTRY_PRODUCER), true);
        }
    }

    private void createOriginal(Workbook workbook, Map<String, List<FindAllOriginalDto>> dataOriginal) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_ORIGINAL);
        Iterator<String> keywords = dataOriginal.keySet().iterator();
        int index = 0;
        String[] data = new String[dataOriginal.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataOriginal(sheet,dataOriginal.get(keyword), index, keyword);
            data[index] = keyword;
            ++index;
        }

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[2] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 12,12);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_ORIGINAL), true);
    }

    private void filledDataOriginal(Sheet sheet, List<FindAllOriginalDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheet.getRow(indexStart) == null) {
                row = sheet.createRow(indexStart);
            } else {
                row = sheet.getRow(indexStart);
            }
            String valueCell = dtos.get(i).getIdOriginal() + "_" + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_ORIGINAL + "!", "").
                    replaceAll("\\d","");
            Name electronicsRange = sheet.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[2] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_ORIGINAL
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
        }
    }

    private void createWards(Workbook workbook, Map<String, List<WardsDto>> dataWards) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_WARDS);
        Iterator<String> keywords = dataWards.keySet().iterator();
        int index = 0;
        String[] data = new String[dataWards.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataWards(sheet,dataWards.get(keyword), index, keyword);
            data[index] = keyword;
            ++index;
        }

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[6] + "\"" + " & $Y4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 25,25);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);

        DataValidationHelper dvHelper_1 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_1 = NAME_INDIRECT + "(\"" + PREFIX[6] + "\"" + " & $AE4)";
        DataValidationConstraint productConstraint_1 = dvHelper_1.createFormulaListConstraint(formula_1);
        CellRangeAddressList productAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 31,31);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_1);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_WARDS), true);
    }

    private void filledDataWards(Sheet sheet, List<WardsDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheet.getRow(indexStart) == null) {
                row = sheet.createRow(indexStart);
            } else {
                row = sheet.getRow(indexStart);
            }
            String valueCell = "STT_" + dtos.get(i).getCodeWard() + "_" + ValueUtil.convertToVietnamese(dtos.get(i).getNameWard()).
                    replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_WARDS + "!", "").
                    replaceAll("\\d","");
            Name electronicsRange = sheet.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[6] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_WARDS
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
        }
    }

    private void createAssetDepartment(Workbook workbook, List<FindAllDepartmentSDto> dataAssetDepartment) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_ASSET_DEPARTMENT);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataAssetDepartment.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataAssetDepartment.get(i).getIdDepartment() + "_" + dataAssetDepartment.get(i).getName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_ASSET_DEPARTMENT + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_ASSET_DEPARTMENT + "!$" + prefix + "$1:" + "$" + prefix + dataAssetDepartment.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList addressList_0 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 10, 10);
            DataValidation categoryValidation_0 = dvHelper.createValidation(categoryConstraint, addressList_0);
            categoryValidation_0.setShowErrorBox(true);
            categoryValidation_0.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_0.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_0.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_0.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_0);
            workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_ASSET_DEPARTMENT), true);
        }
    }

    private void createDistrict(Workbook workbook, Map<String, List<DistrictsDto>> dataDistrict) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_DISTRICTS);
        Iterator<String> keywords = dataDistrict.keySet().iterator();
        int index = 0;
        String[] data = new String[dataDistrict.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();

            filledDataDistricts(sheet,dataDistrict.get(keyword), index, keyword);
            data[index] = keyword;
            ++index;
        }

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[5] + "\"" + " & $X4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 24,24);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);

        DataValidationHelper dvHelper_1 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_1 = NAME_INDIRECT + "(\"" + PREFIX[5] + "\"" + " & $AD4)";
        DataValidationConstraint productConstraint_1 = dvHelper_1.createFormulaListConstraint(formula_1);
        CellRangeAddressList productAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 30,30);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_1);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_DISTRICTS), true);
    }

    private void filledDataDistricts(Sheet sheet, List<DistrictsDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheet.getRow(indexStart) == null) {
                row = sheet.createRow(indexStart);
            } else {
                row = sheet.getRow(indexStart);
            }
            String valueCell = "STT_" + dtos.get(i).getCode() + "_" + ValueUtil.convertToVietnamese(dtos.get(i).getNameDistrict()).
                    replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DISTRICTS + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheet.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[5] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DISTRICTS
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
        }
    }

    private void createProvinces(Workbook workbook, List<ProvincesDto> dataProvinces) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_PROVINCES);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataProvinces.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = "STT_" + dataProvinces.get(i).getCodeProvince() + "_" +
                    ValueUtil.convertToVietnamese(dataProvinces.get(i).getNameProvince()).replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_PROVINCES + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_PROVINCES + "!$" + prefix + "$1:" + "$" + prefix + dataProvinces.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList addressList_0 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 23, 23);
            DataValidation categoryValidation_0 = dvHelper.createValidation(categoryConstraint, addressList_0);
            categoryValidation_0.setShowErrorBox(true);
            categoryValidation_0.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_0.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_0.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_0.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_0);

            CellRangeAddressList addressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 29, 29);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, addressList_1);
            categoryValidation_1.setShowErrorBox(true);
            categoryValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_1.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);
            workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_PROVINCES), true);
        }
    }

    private void createProjects(Workbook workbook, List<FindAllProjectsDto> dataProjects) {
        Sheet sheet = workbook.createSheet(NAME_SHEET_DATA_PROJECTS);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataProjects.size(); i++){
            if (sheet.getRow(i) == null) {
                row = sheet.createRow(i);
            } else {
                row = sheet.getRow(i);
            }
            String valueCell = dataProjects.get(i).getIdProject() + "_" + dataProjects.get(i).getShortName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(indexCell));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_PROJECTS + "!", "").replaceAll("\\d", "");
            String formula = "=" + NAME_SHEET_DATA_PROJECTS + "!$" + prefix + "$1:" + "$" + prefix + dataProjects.size();
            DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
            DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 7, 7);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
            workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_PROJECTS), true);
        }
    }

    private void createDocumentAttack(Workbook workbook, Map<String, List<FindAllLocationDto>> dataDepartment,
                                      Map<String, List<FindAllDocumentAttackDto>> dataDocumentAttack) {
        Sheet sheetDocumentAttack = workbook.createSheet(NAME_SHEET_DATA_DOCUMENT_ATTACK);
        Iterator<String> keywords = dataDepartment.keySet().iterator();
        int index = 0;
        while (keywords.hasNext()){
            String keyword = keywords.next();
            if (!dataDocumentAttack.containsKey(keyword)) {
                filledDataDocumentAttackDefault(sheetDocumentAttack,dataDocumentAttack.get("STT_100_Macdinh"), index, keyword);
            } else {
                dataDocumentAttack.get(keyword).addAll(dataDocumentAttack.get("STT_100_Macdinh"));
                filledDataDocumentAttack(sheetDocumentAttack, dataDocumentAttack.get(keyword), index, keyword);
            }
            ++index;
        }

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[4] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 6,6);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_DOCUMENT_ATTACK), true);
    }

    private void filledDataDocumentAttackDefault(Sheet sheetDocumentAttack, List<FindAllDocumentAttackDto> dtos,
                                                 int index, String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheetDocumentAttack.getRow(indexStart) == null) {
                row = sheetDocumentAttack.createRow(indexStart);
            } else {
                row = sheetDocumentAttack.getRow(indexStart);
            }
            String valueCell = dtos.get(i).getIdDocumentAttack() + "_" + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DOCUMENT_ATTACK + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetDocumentAttack.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[4] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DOCUMENT_ATTACK
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
        }
    }

    private void filledDataDocumentAttack(Sheet sheetDocumentAttack,
                                          List<FindAllDocumentAttackDto> dtos, int index,
                                          String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheetDocumentAttack.getRow(indexStart) == null) {
                row = sheetDocumentAttack.createRow(indexStart);
            } else {
                row = sheetDocumentAttack.getRow(indexStart);
            }
            String valueCell = dtos.get(i).getIdDocumentAttack() + "_" + dtos.get(i).getName();
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DOCUMENT_ATTACK + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetDocumentAttack.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[4] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DOCUMENT_ATTACK
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
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

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[1] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 5,5);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_UNITS), true);
    }

    private void filledDataUnits(Sheet sheetUnit, List<FindAllUnitsDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStart = INDEX_START_FILLED_DATA;
        for (int i = 0; i < dtos.size(); i++) {
            if (sheetUnit.getRow(indexStart) == null) {
                row = sheetUnit.createRow(indexStart);
            } else {
                row = sheetUnit.getRow(indexStart);
            }
            String valueCell = dtos.get(i).getIdUnit() + "_" + dtos.get(i).getNameUnit();
            row.createCell(index).setCellValue(valueCell);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(index));
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_UNITS + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetUnit.getWorkbook().getName(keyword);
            if (electronicsRange == null){
                electronicsRange = sheetUnit.getWorkbook().createName();
                electronicsRange.setNameName(PREFIX[1] + keyword);
            }
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_UNITS
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + indexStart);
        }
    }

    private void createAssetDepartmentImport(Workbook workbook, Map<String,List<FindAllLocationDto>> dataDepartment) {
        Sheet sheetLocation = workbook.createSheet(NAME_SHEET_DATA_LOCATION);
        Iterator<String> keywords = dataDepartment.keySet().iterator();
        int index = 0;
        String[] departments = new String[dataDepartment.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            filledDataLocation(sheetLocation,dataDepartment.get(keyword), index, keyword);
            departments[index] = keyword;
            ++index;
        }

        if (workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY) == null) {
            throw new IllegalArgumentException("Sheet " + NAME_SHEET_IMPORT_ASSET_CATEGORY + " does not exist.");
        }
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        setDataDepartment(departments,workbook);
        String formula = NAME_INDIRECT + "(\"" + PREFIX[3] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 4,4);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_LOCATION), true);
    }

    private void setDataDepartment(String[] departments, Workbook workbook) {
        Sheet sheetDepartment = workbook.createSheet(NAME_SHEET_DATA_DEPARTMENT);
        Row row = null;
        int indexCellDepartment = 0;
        for (int i = 0; i< departments.length; i++){
            if (sheetDepartment.getRow(i) == null) {
                row = sheetDepartment.createRow(i);
            } else {
                row = sheetDepartment.getRow(i);
            }
            row.createCell(indexCellDepartment).setCellValue(departments[i]);
        }
        CellReference cellReference = new CellReference(row.getCell(indexCellDepartment));
        String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DEPARTMENT+"!", "").replaceAll("\\d","");
        String formula = "=" + NAME_SHEET_DATA_DEPARTMENT + "!$" + prefix + "$1:" + "$" + prefix + departments.length;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 3, 3);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_DEPARTMENT), true);
    }

    private void filledDataLocation(Sheet sheetDepartment, List<FindAllLocationDto> dtos, int index, String keyword) {
        Row row = null;
        int indexStartFilled = INDEX_START_FILLED_DATA;
        if (dtos.size() != 0) {
            for (int i = 0; i < dtos.size() ; i++) {
                if (sheetDepartment.getRow(indexStartFilled) == null) {
                    row = sheetDepartment.createRow(indexStartFilled);
                } else {
                    row = sheetDepartment.getRow(indexStartFilled);
                }
                String valueCell = dtos.get(i).getIdLocation() + "_" + dtos.get(i).getName();
                row.createCell(index).setCellValue(valueCell);
                ++indexStartFilled;
            }
            if (row != null) {
                CellReference cellReference = new CellReference(row.getCell(index));
                String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_LOCATION + "!", "").replaceAll("\\d","");
                Name electronicsRange = sheetDepartment.getWorkbook().createName();
                electronicsRange.setNameName(PREFIX[3] + keyword);
                electronicsRange.setRefersToFormula(NAME_SHEET_DATA_LOCATION
                        + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                        + ":$" + prefix + "$" + indexStartFilled);
            }
        } else {
            if (sheetDepartment.getRow(indexStartFilled) == null) {
                row = sheetDepartment.createRow(indexStartFilled);
            } else {
                row = sheetDepartment.getRow(indexStartFilled);
            }
            if (row != null) {
                row.createCell(index).setCellValue("Không có");
                CellReference cellReference = new CellReference(row.getCell(index));
                String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_LOCATION + "!", "").replaceAll("\\d","");
                Name electronicsRange = sheetDepartment.getWorkbook().createName();
                electronicsRange.setNameName(PREFIX[3] + keyword);
                electronicsRange.setRefersToFormula(NAME_SHEET_DATA_LOCATION
                        + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                        + ":$" + prefix + "$" + indexStartFilled);
            }
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
        /**
         *  Cột danh mục tài sản
         * */
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(assetCategories);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 0, 0);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
        /**
         *  Cột loại tài sản
         * */
        String formula = NAME_INDIRECT + "(\"" + PREFIX[0] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 1,1);
        DataValidation subCategoryValidation = dvHelper.createValidation(productConstraint, productAddressList);
        subCategoryValidation.setShowErrorBox(true);
        subCategoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        subCategoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        subCategoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        subCategoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(subCategoryValidation);

        /**
         *  Cột khấu hao và hao mòn
         * */
        for (int rowIndex = TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW; rowIndex <= TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW; rowIndex++) {
            Row row = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getRow(rowIndex);
            if (row == null) {
                row = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).createRow(rowIndex); // Create row if it doesn't exist
            }
            Cell cellMinDepreciation = row.createCell(142);
            Cell cellMaxDepreciation = row.createCell(143);
            Cell cellValueWearTear  = row.createCell(137);
            Cell cellYearUsedWearTear = row.createCell(136);

            String formulaMinDepreciation = "IF(NOT(ISBLANK($B"+ (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) +"," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$E,2,0),\"\")";
            String formulaMaxDepreciation = "IF(NOT(ISBLANK($B"+ (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) +"," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$E,3,0),\"\")";
            String formulaValueWearTear = "IF(NOT(ISBLANK($B"+ (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) +"," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$E,4,0),\"\")";
            String formulaYearUsedWearTear = "IF(NOT(ISBLANK($B"+ (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) +"," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$A:$E,5,0),\"\")";
            cellMinDepreciation.setCellFormula(formulaMinDepreciation);
            cellMaxDepreciation.setCellFormula(formulaMaxDepreciation);
            cellValueWearTear.setCellFormula(formulaValueWearTear);
            cellYearUsedWearTear.setCellFormula(formulaYearUsedWearTear);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(NAME_SHEET_DATA_ASSET_CATEGORY), true);
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
            String valueCell = dtos.get(indexStart).getIdAssetCategory() + "_" + dtos.get(indexStart).getName();
            String minimumTimeDepreciation = dtos.get(indexStart).getMinimumTimeDepreciation();
            String maximumTimeDepreciation = dtos.get(indexStart).getMaximumTimeDepreciation();
            String valueWearTear = dtos.get(indexStart).getValueWearTear();
            String yearWearTear = dtos.get(indexStart).getYearUsedWearTear();
            row.createCell(0).setCellValue(valueCell);
            row.createCell(1).setCellValue(minimumTimeDepreciation);
            row.createCell(2).setCellValue(maximumTimeDepreciation);
            row.createCell(3).setCellValue(valueWearTear);
            row.createCell(4).setCellValue(yearWearTear);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(0));
            String prefix =  cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_ASSET_CATEGORY + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetAssetCategories.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[0] + keywords);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_ASSET_CATEGORY
                    + "!$" + prefix + "$" + (index + 1)
                    + ":$" + prefix + "$" + dtosLength);
        }
        return dtosLength;
    }
}
