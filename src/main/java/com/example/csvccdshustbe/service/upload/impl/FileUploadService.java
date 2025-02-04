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
import com.example.csvccdshustbe.dto.report.inventory.BlueprintInventoryReportDto;
import com.example.csvccdshustbe.dto.report.inventory.CouncilInventoryReportDto;
import com.example.csvccdshustbe.dto.report.inventory.FindAllAssetForInventoryReportDto;
import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.dto.typeUse.FindAllTypeUseDto;
import com.example.csvccdshustbe.dto.unit.FindAllUnitsDto;
import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.dto.wards.WardsDto;
import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.repository.report.ReportRepository;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
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
import com.example.csvccdshustbe.service.toolCategories.ToolCategoriesService;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.wards.WardsService;
import com.example.csvccdshustbe.utility.*;
import com.example.csvccdshustbe.utility.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private static final String NAME_SHEET_CATEGORY_VIEW = "DanhsachLoaiTaiSan";
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
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ToolCategoriesService toolCategoriesService;

    @Override
    public  String saveAndReturnPathAsset(MultipartFile uploadedFile, String folderName) throws IOException, FileException {
        FileUtil.checkFileAImportAsset(uploadedFile);
        return saveFile(uploadedFile, folderName);
    }

    @Override
    public void deleteByPathFile(String pathFile, String originalFile, String destinationFile) throws ValidateFiledException, IOException, InterruptedException {
        if (StringUtils.isBlank(pathFile)){
            throw new ValidateFiledException("validate data request");
        }
        pathFile = pathFile.replace(originalFile,destinationFile);
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

    private String saveFilesAsset(MultipartFile[] multipartFiles,  String folderName) throws IOException {
        String folderSave = PropertiesUtil.getProperty("hust.csvc.static.location.upload.data");
        log.info("Folder save: {}", folderSave);
        List<String> pathFilesResponses = new ArrayList<>();
        String fileId;
        String folder;
        String fileReturn;
        for (MultipartFile multipartFile : multipartFiles) {
            fileId = generateFileId();
            folder = folderSave + buildFolderUpload(folderName);
            File inFiles = new File(folder);
            log.info("Folder: {}", folder);
            if (!inFiles.exists() && !inFiles.mkdirs()) {
                log.error("Can't create folder");
            }
            String namePathFileResponse = folder
                    + SEPARATOR
                    + fileId
                    + "_"
                    + FilenameUtils.getBaseName(multipartFile.getOriginalFilename())
                    + "."
                    + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            File file = new File(namePathFileResponse);
            try {
                if (file.exists()) {
                    file.delete();
                }
                log.info("File: {}", namePathFileResponse);
                FileUtils.touch(file);
                multipartFile.transferTo(file);
                fileReturn = namePathFileResponse.replace(folderSave, PropertiesUtil.getProperty("hust.csvc.static.location.path.static.upload.data"));
            } catch (IOException e) {
                log.error("Can't not save file, file error!", e);
                throw new IOException("Can't not save file, file error!");
            }
            pathFilesResponses.add(fileReturn);
        }
        return String.join(";",pathFilesResponses);
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
        String fileExcel = PropertiesUtil.getProperty("hust.csvc.static.location.resources.static") + SEPARATOR
                + "Sample_Excel_Import_Asset.xlsx";
//        String fileExcel = "D:\\CompanyBk\\Sample_Excel_Import_Asset_root.xlsx";
        FileInputStream file = new FileInputStream(new File(fileExcel));

        Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategory =
                assetCategoriesService.findAllAssetCategoriesVisibleResponseToDownload();
        Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategoryToView =
                assetCategoriesService.findAllAssetCategoriesVisibleResponseToView();
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
        createAssetCategoriesImport(workbook, mapAssetCategory,mapAssetCategoryToView);
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
//        String fileFinal = "D:\\CompanyBk\\Sample_Excel_Import_Asset_final_5.xlsx";
        log.info("File final:" + fileFinal);
        File filePathOutput = FileUtil.createFileSampleAsset(fileFinal);
        String fileReturn = fileFinal.replace(root, PropertiesUtil.getProperty("hust.csvc.static.location.static.files"));
        log.info("File return: " + fileReturn);
        try (FileOutputStream fileOut = new FileOutputStream(fileFinal)) {
            workbook.write(fileOut);
            workbook.close();
//            return fileFinal;
            return fileReturn;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String downloadInventoryReportByCodeDocument(FindAllAssetProcessRequest request) throws IOException {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        request.getIdsDepartmentOriginal().add(Constants.DEFAULT_ASSET_CATEGORY);
        String fileExcel = PropertiesUtil.getProperty("hust.csvc.static.location.resources.static")
                + SEPARATOR
                + FileUtil.FOLDER_NAME_REPORT
                + SEPARATOR
                + Constants.NAME_REPORTS[36];
//        String fileExcel = "D:\\CompanyBK\\csvc-hust\\src\\main\\resources\\static\\reports\\Biểu mẫu 01-TSCĐ cơ quan, tổ chức, đơn vị.xlsx";
        List<FindAllAssetForInventoryReportDto> assetReport =
                reportRepository.findInfoAssetForInventoryReportByCodeDocument(request);
        BlueprintInventoryReportDto council = reportRepository.findBlueprintInventoryReportDtoByCodeDocument(request);
        FileInputStream file = new FileInputStream(new File(fileExcel));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        writeDataBlueprintInventoryReport(sheet, council);
        writeDataAssetInventoryReport(sheet, assetReport, 3);//council.getCouncilInventoryReportDtos().size()
        String fileFinal = createFileExportInventoryReport();
        File filePathOutput = FileUtil.createFileSampleAsset(fileFinal);
        String fileReturn = fileFinal.replace(PropertiesUtil.getProperty("hust.csvc.static.location.tomcat.webapp.csvcbe")
                , PropertiesUtil.getProperty("hust.csvc.static.location.static.files"));
//        String filePathOutput = "D:\\CompanyBK\\Biểu mẫu 01-TSCĐ cơ quan, tổ chức, đơn vị.xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePathOutput)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return fileReturn;
//        return filePathOutput;
    }

    private String createFileExportInventoryReport() {
        String root = PropertiesUtil.getProperty("hust.csvc.static.location.tomcat.webapp.csvcbe");
        String folder = root + SEPARATOR + FileUtil.FOLDER_NAME_REPORT + SEPARATOR + FileUtil.getFolderInfo();
        FileUtil.createFolder(folder);
        return folder + SEPARATOR + "Inventory_Report_" + new Date().getTime() + "." + ExcelUtil.FILE_EXCEL[1];
    }

    private void writeDataAssetInventoryReport(Sheet sheet,
                                               List<FindAllAssetForInventoryReportDto> assetReport,
                                               int sizeIncrease) throws JsonProcessingException {
        int rowStart = 18;
        int rowNeeded = 0;
        if(sizeIncrease > 3) {
            rowNeeded = sizeIncrease - 3;
            rowStart = rowStart + rowNeeded;
        }
        sheet.shiftRows(rowStart , sheet.getPhysicalNumberOfRows(), Math.max(assetReport.size() - 118, 1),  true, true);
        ObjectMapper objectMapper = new ObjectMapper();
        int index = 0;
        for (FindAllAssetForInventoryReportDto asset : assetReport){
            if (index == 0) {
                index++;
                continue;
            }
            writeValueCell(sheet, rowStart, 0, ValueUtil.getStringByObject(index), null);
            index++;
            writeValueCell(sheet, rowStart, 1, asset.getNameAssetCategory() == null ? "" : asset.getNameAssetCategory(), null);
            writeValueCell(sheet, rowStart, 2, asset.getNumberCodePattern() == null ? "" : asset.getNumberCodePattern(), null);
            HashMap<String, Object> dataAsset = objectMapper.readValue(asset.getValue() == null ? "{}" : asset.getValue(), new TypeReference<>() {});
            writeValueCell(sheet, rowStart, 3, ValueUtil.getStringByObject(dataAsset.get("year_use")), null);
            writeValueCell(sheet, rowStart, 4, ValueUtil.getStringByObject(dataAsset.get("unit")), null);
            writeValueCell(sheet, rowStart, 5, ValueUtil.getStringByObject(dataAsset.get("quantity")), null);
            writeValueCell(sheet, rowStart, 6, ValueUtil.getStringByObject(dataAsset.get("quantity_inventory")), null);
            writeValueCell(sheet, rowStart, 7, ValueUtil.getStringByObject(dataAsset.get("quantity_difference")), null);
            writeValueCell(sheet, rowStart, 8, Objects.equals(ValueUtil.getStringByObject(dataAsset.get("type_target")), "3") ? "m2" : " ", null);
            writeValueCell(sheet, rowStart, 9, Objects.equals(ValueUtil.getStringByObject(dataAsset.get("acreage")), "") ? "" : ValueUtil.getStringByObject(dataAsset.get("acreage")),null);
            writeValueCell(sheet, rowStart, 10,Objects.equals(ValueUtil.getStringByObject(dataAsset.get("acreage_inventory")), "") ? "" : ValueUtil.getStringByObject(dataAsset.get("acreage_inventory")), null);
            writeValueCell(sheet, rowStart, 11, ValueUtil.getStringByObject(dataAsset.get("acreage_difference")), null);
            writeValueCell(sheet, rowStart, 12, ValueUtil.getStringByObject(dataAsset.get("original_of_formation")), null);
            writeValueCell(sheet, rowStart, 13, ValueUtil.getStringByObject(dataAsset.get("rest_value")), null);
            writeValueCell(sheet, rowStart, 14, ValueUtil.getStringByObject(dataAsset.get("recorded_accounting")), null);
            if(asset.getValue() != null){
                writeValueCell(sheet, rowStart, (Objects.equals(ValueUtil.getStringByObject(dataAsset.get("is_increase")), "1") ||
                        ValueUtil.getStringByObject(dataAsset.get("is_increase")) == null ||
                        ValueUtil.getStringByObject(dataAsset.get("is_increase")).isEmpty()) ? 14 : 15, "1", null);
                writeValueCell(sheet, rowStart, (Objects.equals(ValueUtil.getStringByObject(dataAsset.get("status_use")), "1") ||
                                ValueUtil.getStringByObject(dataAsset.get("status_use")) == null ||
                                ValueUtil.getStringByObject(dataAsset.get("status_use")).isEmpty()) ? 16 : 17, "1", null);
            }
//            setStt(sheet, rowStart, asset.getDepth(), Objects.equals(asset.getNumberCodePattern(), "null") || asset.getNumberCodePattern() == null ? "0" : asset.getNumberCodePattern());
            addBoldBorderToRows(sheet, rowStart, 1, 0, 17, true);
            setTypeRow(sheet, rowStart, asset.getDepth());
            ++rowStart;
        }
    }

    public void setTypeRow(Sheet sheet, int rowStart, int depth) {
        Row row = sheet.getRow(rowStart);
        if (row == null) {
            row = sheet.createRow(rowStart);
        }
        Workbook workbook = sheet.getWorkbook();

        CellStyle styleBC = workbook.createCellStyle();
        Font fontBC = workbook.createFont();
        fontBC.setFontName("Times New Roman");
        fontBC.setFontHeightInPoints((short) 13);
        styleBC.setWrapText(true);
        styleBC.setVerticalAlignment(VerticalAlignment.CENTER);
        styleBC.setAlignment(HorizontalAlignment.LEFT);
        styleBC.setBorderTop(BorderStyle.THIN);
        styleBC.setBorderBottom(BorderStyle.THIN);
        styleBC.setBorderLeft(BorderStyle.THIN);
        styleBC.setBorderRight(BorderStyle.THIN);
        styleBC.setFont(fontBC);

        CellStyle styleA = workbook.createCellStyle();
        styleA.cloneStyleFrom(styleBC);
        styleA.setAlignment(HorizontalAlignment.CENTER);

        if (depth == 1 || depth == 2) {
            fontBC.setBold(true);
        } else if (depth == 3) {
            fontBC.setBold(true);
            fontBC.setItalic(true);
        } else if (depth == 4) {
            fontBC.setItalic(true);
        }

        for (int col = 0; col <= 2; col++) {
            Cell cell = row.getCell(col);
            if (cell == null) {
                cell = row.createCell(col);
            }

            if (col == 0) {
                cell.setCellStyle(styleA);
            } else if (col == 1 || col == 2) {
                cell.setCellStyle(styleBC);
            }
        }
    }


    public void setStt(Sheet sheet, int rowStart, int depth, String numberCodePattern) {
        Row row = sheet.getRow(rowStart);
        if (row == null) {
            row = sheet.createRow(rowStart);
        }
        String numberCode = "";
        if (depth < 5){
             numberCode = convertToHierarchy(numberCodePattern);
        }
        Cell cellA = row.getCell(0);
        if (cellA == null) {
            cellA = row.createCell(0);
        }
        cellA.setCellValue(numberCode);
    }

    private String convertToHierarchy(String input) {
        if (input == null || input.length() < 3) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        if (input.length() >= 3) {
            result.append(input.charAt(2));
        }
        for (int i = 3; i < input.length(); i += 2) {
            int end = Math.min(i + 2, input.length());
            String group = input.substring(i, end);

            group = group.replaceAll("^0+", "");

            if (!group.isEmpty()) {
                result.append(".").append(group);
            }
        }

        return result.toString();
    }

    public static void formatCell(Sheet sheet, int rowIndex, int colIndex, boolean isBold) {

        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 13);
        font.setBold(isBold);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        cell.setCellStyle(cellStyle);
    }

    private void addBoldBorderToRows(Sheet sheet, int rowStart, int rowNeeded, int colStart, int colEnd, boolean addBorder) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        if (addBorder) {
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
        }

        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 13);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int rowIndex = rowStart; rowIndex < rowStart + rowNeeded; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            for (int col = colStart; col <= colEnd; col++) {
                Cell cell = row.getCell(col);
                if (cell == null) {
                    cell = row.createCell(col);
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private void writeDataBlueprintInventoryReport(Sheet sheet, BlueprintInventoryReportDto council) {
        setInformationDepartmentInventoryReport(sheet, council);
        setInformationCouncilInventoryReport(sheet, council);
    }

    private void setInformationCouncilInventoryReport(Sheet sheet, BlueprintInventoryReportDto council) {
        setInformationDateInventoryReport(sheet, council.getTimeInventory());
        setInformationDetailsCouncilInventoryReport(sheet, council.getCouncilInventoryReportDtos());
    }

    private void setInformationDetailsCouncilInventoryReport(Sheet sheet, List<CouncilInventoryReportDto> councilDtos) {
        int indexRowStart = 9;
        int indexColStart = 0;

        int rowsNeeded = councilDtos.size();

        int totalRows = sheet.getPhysicalNumberOfRows() ;

        if (totalRows >= indexRowStart && rowsNeeded > 3) {
            sheet.shiftRows(indexRowStart, totalRows , rowsNeeded - 3,  true, true);
        }
        int stt = 1;
        for (CouncilInventoryReportDto councilDto: councilDtos) {
            writeValueCell(sheet, indexRowStart, indexColStart, stt +
                    ". Ông/Bà....." + (councilDto.getFullName() != null ? councilDto.getFullName() : "...") + "........."
                            + "Chức vụ:....." + (councilDto.getPosition() != null ? councilDto.getPosition() : "...") + "........."
                            +  "Vai trò:....." + (councilDto.getInstancePosition() != null ? councilDto.getInstancePosition() : "...") + ".........", null);
            formatCell(sheet, indexRowStart, 0, false);
            ++indexRowStart;
            ++stt;
        }
    }

    private void setInformationDateInventoryReport(Sheet sheet, String timeInventory) {
        LocalDate localDate = LocalDate.parse(timeInventory, DateTimeFormatter.ofPattern(DateUtil.DDMMYYYY));
        writeValueCell(sheet, 7,0,"Hôm nay, "
                + "ngày.." + localDate.getDayOfMonth() + "....."
                + "tháng.." + localDate.getMonthValue() + "....."
                + "năm.." + localDate.getYear() + "....." , null);
        formatCell(sheet,7,0, false);
    }

    private void setInformationDepartmentInventoryReport(Sheet sheet, BlueprintInventoryReportDto council) {
        writeValueCell(sheet, 2, 1, "Tên đơn vị kiểm kê: " + (council.getNameDepartment() != null ? council.getNameDepartment() : "...") + "(*)", true);
        formatCell(sheet, 2,1, true);
        writeValueCell(sheet, 13, 0, "Đã tiến hành kiểm kê tài sản công là tài sản cố định tại cơ quan, tổ chức, đơn vị do "
                + (council.getNameDepartment() != null ? council.getNameDepartment() : "...") + "(*)  quản lý/tạm quản lý, kết quả như sau:", false);
        formatCell(sheet, 13, 0, false);
    }

    @Override
    public String downLoadRevaluationReport(Integer idAssetProcess, Integer status) throws IOException {
        String fileExcel = PropertiesUtil.getProperty("hust.csvc.static.location.resources.static.reports") + SEPARATOR
                + "36_C52 -HD_Bien ban danh gia lai TSCD.xlsx";
        //String fileExcel = "E:\csvc\src\main\resources\static\reports\36_C52 -HD_Bien ban danh gia lai TSCD.xlsx";

        Optional<List<Object[]>> assetReport = reportRepository.findInfoAssetForRevaluationReport(idAssetProcess, status);
        Optional<List<Object[]>> stakeHoder = reportRepository.findInfoStakeHolderForRevaluationReport(idAssetProcess);

        FileInputStream file = new FileInputStream(new File(fileExcel));
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        if (stakeHoder.isPresent()) {
            List<Object[]> resultListStakeHoder = stakeHoder.get();
            int startingRow = 11;
            int rowsNeeded = resultListStakeHoder.size();

            int totalRows = sheet.getPhysicalNumberOfRows() ;

            if (totalRows >= startingRow && rowsNeeded > 3) {
                sheet.shiftRows(startingRow, totalRows , rowsNeeded - 3,  true, true);
            }

            if (!resultListStakeHoder.isEmpty()) {
                for (int i = 0; i < resultListStakeHoder.size(); i++) {
                    Object[] row = resultListStakeHoder.get(i);
                    String user = "- Ông/Bà " + (row[0] != null ? row[0] : "....................")
                            + " chức vụ " + (row[1] != null ? row[1] : "....................")
                            + " đại diện " + (row[2] != null ? row[2] : "...................");

                    updateCell(sheet, startingRow + i, 2, user);
                }
            }
        }

        if (assetReport.isPresent()) {
            List<Object[]> resultList = assetReport.get();

            int startingRow = 20;
            int rowsNeeded = resultList.size();

            int totalRows = sheet.getPhysicalNumberOfRows() ;
            if (totalRows >= startingRow && rowsNeeded > 5) {
                sheet.shiftRows(startingRow, totalRows , rowsNeeded - 5,  true, true);
            }

            if (!resultList.isEmpty()) {
                Object[] firstRow = resultList.get(0);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("'Ngày' d 'tháng' M 'năm' yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("'ngày' d 'tháng' M 'năm' yyyy");
                String formattedDate1 = now.format(formatter1);
                String formattedDate2 = now.format(formatter2);
                String reportTime2 = "Căn cứ quyết định số:.......... " + formattedDate2 + " của ..........................." +
                        "về việc đánh giá lại TSCĐ" ;
                String value = (String) firstRow[1];

                String oldDepreciationValue = extractValue(value, "\"old_information\": \\{.*?\"depreciation\": \\{.*?\"valueDepreciation\": \"(\\d+)\"");
                String oldRestValue = extractValue(value, "\"old_information\": \\{.*?\"depreciation\": \\{.*?\"restValue\": \"(\\d+)\"");
                String oldCumulativeValue = extractValue(value, "\"old_information\": \\{.*?\"depreciation\": \\{.*?\"cumulative\": \"(\\d+)\"");
                String oldName = extractValue(value, "\"old_information\": \\{.*?\"common\": \\{.*?\"name\": \"([^\"]+)\"");
                String oldCodeAsset = extractValue(value, "\"old_information\": \\{.*?\"common\": \\{.*?\"codeAsset\": \"([^\"]+)\"");

                // Lấy thông tin về "new_information"
                String newDepreciationValue = extractValue(value, "\"new_information\": \\{.*?\"depreciation\": \\{.*?\"valueDepreciation\": \"(\\d+)\"");
                String newRestValue = extractValue(value, "\"new_information\": \\{.*?\"depreciation\": \\{.*?\"restValue\": \"(\\d+)\"");
                String newCumulativeValue = extractValue(value, "\"new_information\": \\{.*?\"depreciation\": \\{.*?\"cumulative\": \"(\\d+)\"");

                sheet.shiftColumns(7, sheet.getRow(19).getLastCellNum() - 1, 2);

                updateCell(sheet, 6, 1, formattedDate1);
                updateCell(sheet, 10, 2, reportTime2);
                updateCell(sheet, 16, 1, "1");
                updateCell(sheet, 20, 2, oldName + "-" + oldCodeAsset);
                updateCell(sheet, 20, 5, oldDepreciationValue);
                updateCell(sheet, 20, 6, oldRestValue);
                updateCell(sheet, 20, 7, oldCumulativeValue);
                updateCell(sheet, 20, 8, newDepreciationValue);
                updateCell(sheet, 20, 9, newRestValue);
                updateCell(sheet, 20, 10, newCumulativeValue);

            }

        }



        String root = PropertiesUtil.getProperty("hust.csvc.static.location.tomcat.webapp.csvcbe");
        String folder = root + SEPARATOR + "Reports" + SEPARATOR + FileUtil.getFolderInfo();
        FileUtil.createFolder(folder);
        String fileFinal = folder + SEPARATOR + "Revaluation_Report_" + new Date().getTime() + ".xlsx";
        log.info("File final: " + fileFinal);

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

    private void updateCell(Sheet sheet, int rowIndex, int colIndex, String content) {
        rowIndex--;
        colIndex--;
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(content);
    }


    private void writeValueCell(Sheet sheet, int rowIndex, int colIndex, String content, Boolean isBold) {
        Workbook workbook = sheet.getWorkbook();
        Font font = workbook.createFont();
        if (isBold != null && isBold) {
            font.setBold(true);
        }
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(content);
        cell.setCellStyle(style);
    }

    public static String extractValue(String jsonString, String regex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(jsonString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public String downLoadReportByPathFile(String pathFileReport) throws IOException {
        String file = PropertiesUtil.getProperty("hust.csvc.static.location.static.files") + pathFileReport;
        return file;
    }

    @Override
    public String updateFilesAttached(MultipartFile[] files, String folderName) throws FileException, IOException {
        switch (folderName){
            case FileUtil.FOLDER_ASSET -> {
                return updateFilesAttachedAsset(files,folderName);
            }
        }
        return null;
    }

    @Override
    public String downloadFileImportTool() throws IOException {
        String fileExcel = PropertiesUtil.getProperty("hust.csvc.static.location.resources.static") + SEPARATOR
                + "Sample_Excel_Import_Tool.xlsx";
        FileInputStream file = new FileInputStream(new File(fileExcel));
        List<FindAllToolCategoryDto> toolCategoryToSelected = toolCategoriesService.findAllToolCategoriesToDownloadAndSelected();
        List<FindAllToolCategoryDto> toolCategoryToView = toolCategoriesService.findAllToolCategoriesToDownloadAndView();
        // Get suppliers
//        List<>
        // Get Point decision
        // Get projects
        // Get originals
        // Get original of formation
        // Get units
        // Get departments
        // Get locations
        // Get user
        // Get medicine categories
        // Get medicine type
        Workbook workbook = new XSSFWorkbook(file);
        return null;
    }

    private String updateFilesAttachedAsset(MultipartFile[] files, String folderName) throws FileException, IOException {
        FileUtil.checkFileAsset(files);
        return saveFilesAsset(files, folderName);
    }

    private void createDataOriginalOfFormation(Workbook workbook, List<FindAllOriginalOfFormationDto> dataOriginalOfFormation) {
        Sheet sheet = workbook.getSheet(NAME_SHEET_EXPLAIN);
        Row row = null;
        int indexCell = 15;
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 122, 122);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 97, 97);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 96, 96);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 65, 65);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 66, 66);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation_1.setShowErrorBox(true);
            categoryValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_1.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 85, 85);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation_2.setShowErrorBox(true);
            categoryValidation_2.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_2.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_2.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_2.setShowPromptBox(true);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 24, 24);
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
            categoryValidation_1.setShowErrorBox(true);
            categoryValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_1.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 84, 84);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation_2.setShowErrorBox(true);
            categoryValidation_2.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_2.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_2.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_2.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_2);


            CellRangeAddressList categoryAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 88, 88);
            DataValidation categoryValidation_3 = dvHelper.createValidation(categoryConstraint, categoryAddressList_3);
            categoryValidation_3.setShowErrorBox(true);
            categoryValidation_3.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_3.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_3.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_3.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_3);


            CellRangeAddressList categoryAddressList_4 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 95, 95);
            DataValidation categoryValidation_4 = dvHelper.createValidation(categoryConstraint, categoryAddressList_4);
            categoryValidation_4.setShowErrorBox(true);
            categoryValidation_4.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_4.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_4.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_4.setShowPromptBox(true);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 30, 30);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Chọn tài sản khuôn viên đất nếu tài sản Nhà \"Có quản lý đất\".");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 38, 38);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation_1.setShowErrorBox(true);
            categoryValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_1.createPromptBox(PROMPT, "Chọn khuôn viên đất nếu Vật kiến trúc thuộc khuôn viên nào đó.");
            categoryValidation_1.setShowPromptBox(true);
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
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 23,23);
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
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 63,63);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation_1.setShowErrorBox(true);
        productValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation_1.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_1);

        DataValidationHelper dvHelper_2 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_2 = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint_2 = dvHelper_2.createFormulaListConstraint(formula_2);
        CellRangeAddressList productAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 83,83);
        DataValidation productValidation_2 = dvHelper.createValidation(productConstraint_2, productAddressList_2);
        productValidation_2.setShowErrorBox(true);
        productValidation_2.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation_2.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation_2.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation_2.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation_2);


        DataValidationHelper dvHelper_3 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_3 = NAME_INDIRECT + "(\"" + PREFIX[8] + "\"" + " & $D4)";
        DataValidationConstraint productConstraint_3 = dvHelper_3.createFormulaListConstraint(formula_3);
        CellRangeAddressList productAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 94,94);
        DataValidation productValidation_3 = dvHelper.createValidation(productConstraint_3, productAddressList_3);
        productValidation_3.setShowErrorBox(true);
        productValidation_3.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation_3.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation_3.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation_3.setShowPromptBox(true);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 22, 22);
            DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
            categoryValidation.setShowErrorBox(true);
            categoryValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

            CellRangeAddressList categoryAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 43, 43);
            DataValidation categoryValidation_1 = dvHelper.createValidation(categoryConstraint, categoryAddressList_1);
            categoryValidation_1.setShowErrorBox(true);
            categoryValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_1.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_1);

            CellRangeAddressList categoryAddressList_2 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 57, 57);
            DataValidation categoryValidation_2 = dvHelper.createValidation(categoryConstraint, categoryAddressList_2);
            categoryValidation_2.setShowErrorBox(true);
            categoryValidation_2.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_2.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_2.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_2.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_2);

            CellRangeAddressList categoryAddressList_3 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 77, 77);
            DataValidation categoryValidation_3 = dvHelper.createValidation(categoryConstraint, categoryAddressList_3);
            categoryValidation_3.setShowErrorBox(true);
            categoryValidation_3.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_3.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_3.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_3.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_3);

            CellRangeAddressList categoryAddressList_4 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 93, 93);
            DataValidation categoryValidation_4 = dvHelper.createValidation(categoryConstraint, categoryAddressList_4);
            categoryValidation_4.setShowErrorBox(true);
            categoryValidation_4.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_4.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_4.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_4.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_4);

            CellRangeAddressList categoryAddressList_5 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 87, 87);
            DataValidation categoryValidation_5 = dvHelper.createValidation(categoryConstraint, categoryAddressList_5);
            categoryValidation_5.setShowErrorBox(true);
            categoryValidation_5.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_5.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_5.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_5.setShowPromptBox(true);
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
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 14,14);
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
        String formula = NAME_INDIRECT + "(\"" + PREFIX[6] + "\"" + " & $AA4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 27,27);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);

        DataValidationHelper dvHelper_1 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_1 = NAME_INDIRECT + "(\"" + PREFIX[6] + "\"" + " & $AG4)";
        DataValidationConstraint productConstraint_1 = dvHelper_1.createFormulaListConstraint(formula_1);
        CellRangeAddressList productAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 33,33);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation_1.setShowErrorBox(true);
        productValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation_1.setShowPromptBox(true);
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
        String formula = NAME_INDIRECT + "(\"" + PREFIX[5] + "\"" + " & $Z4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 26,26);
        DataValidation productValidation = dvHelper.createValidation(productConstraint, productAddressList);
        productValidation.setShowErrorBox(true);
        productValidation.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);

        DataValidationHelper dvHelper_1 = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula_1 = NAME_INDIRECT + "(\"" + PREFIX[5] + "\"" + " & $AF4)";
        DataValidationConstraint productConstraint_1 = dvHelper_1.createFormulaListConstraint(formula_1);
        CellRangeAddressList productAddressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 32,32);
        DataValidation productValidation_1 = dvHelper.createValidation(productConstraint_1, productAddressList_1);
        productValidation_1.setShowErrorBox(true);
        productValidation_1.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
        productValidation_1.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation_1.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
        productValidation_1.setShowPromptBox(true);
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
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 25, 25);
            DataValidation categoryValidation_0 = dvHelper.createValidation(categoryConstraint, addressList_0);
            categoryValidation_0.setShowErrorBox(true);
            categoryValidation_0.createErrorBox(ERROR, "Không được phép sử dụng văn bản tùy chỉnh, vui lòng chọn từ danh sách thả xuống.");
            categoryValidation_0.setErrorStyle(DataValidation.ErrorStyle.STOP);
            categoryValidation_0.createPromptBox(PROMPT, "Vui lòng nhấp vào mục thả xuống.");
            categoryValidation_0.setShowPromptBox(true);
            workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation_0);

            CellRangeAddressList addressList_1 = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                    TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 31, 31);
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

    private void createAssetCategoriesImport(Workbook workbook,
                                             Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategory,
                                             Map<String, List<FindAllAssetCategoriesToDownloadDto>> mapAssetCategoryView) {
        Sheet sheetAssetCategories = workbook.createSheet(NAME_SHEET_DATA_ASSET_CATEGORY);
        Sheet sheetCategoriesView = workbook.getSheet(NAME_SHEET_CATEGORY_VIEW);
        Row headerRow = sheetAssetCategories.createRow(0);
        String[] headers = {"Mã số tài sản", "Tên Loại tài sản", "Thời gian trích khấu hao tối thiểu", "Thời gian trích khấu hao tối đa",
                "Tỷ lệ Hao Mòn", "Số Năm sử dụng"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        Iterator<String> keywords = mapAssetCategory.keySet().iterator();
        Iterator<String> keywordsView = mapAssetCategoryView.keySet().iterator();
        int index = 0;
        int indexView = 0;
        int indexFilledData = 1;
        int indexFilledDataView = 1;
        String[] assetCategories = new String[mapAssetCategory.size()];
        String[] assetCategoryView = new String[mapAssetCategoryView.size()];
        while (keywords.hasNext()){
            String keyword = keywords.next();
            indexFilledData= filledDataAssetCategory(sheetAssetCategories,mapAssetCategory.get(keyword), indexFilledData, keyword);
            assetCategories[index] = keyword;
            ++index;
            ++indexFilledData;
        }
        while (keywordsView.hasNext()){
            String keyword = keywordsView.next();
            indexFilledDataView= filledDataAssetCategoryView(sheetCategoriesView,mapAssetCategoryView.get(keyword), indexFilledDataView, keyword);
            assetCategoryView[indexView] = keyword;
            ++indexView;
            ++indexFilledDataView;
        }
        /**
         *  Cột danh mục tài sản
         * */
        Sheet hiddenSheet = workbook.createSheet("InstancesCategories");
        for (int i = 0; i < assetCategories.length; i++) {
            Row row = hiddenSheet.createRow(i);
            row.createCell(0).setCellValue(assetCategories[i]);
        }

        workbook.setSheetHidden(workbook.getSheetIndex("InstancesCategories"), true);
        String InstancesCategories = "InstancesCategories!$A$1:$A$" + assetCategories.length;

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
//        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(assetCategories);
        DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(InstancesCategories);
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
            Cell cellMinDepreciation = row.createCell(145);
            Cell cellMaxDepreciation = row.createCell(146);
            Cell cellValueWearTear  = row.createCell(140);
            Cell cellYearUsedWearTear = row.createCell(139);

            String formulaMinDepreciation = "IF(NOT(ISBLANK($B" + (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) + "," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$B:$F,2,0),\"\")";
            String formulaMaxDepreciation = "IF(NOT(ISBLANK($B" + (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) + "," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$B:$F,3,0),\"\")";
            String formulaValueWearTear = "IF(NOT(ISBLANK($B" + (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) + "," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$B:$F,4,0),\"\")";
            String formulaYearUsedWearTear = "IF(NOT(ISBLANK($B" + (rowIndex + 1) + ")),VLOOKUP($B" + (rowIndex + 1) + "," + NAME_SHEET_DATA_ASSET_CATEGORY + "!$B:$F,5,0),\"\")";
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
            String numberCodePattern=dtos.get(indexStart).getNumberCodePattern();
            row.createCell(0).setCellValue(numberCodePattern);
            row.createCell(1).setCellValue(valueCell);
            row.createCell(2).setCellValue(minimumTimeDepreciation);
            row.createCell(3).setCellValue(maximumTimeDepreciation);
            row.createCell(4).setCellValue(valueWearTear);
            row.createCell(5).setCellValue(yearWearTear);
            ++indexStart;
        }
        if (row != null) {
            CellReference cellReference = new CellReference(row.getCell(1));
            String prefix =  cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_ASSET_CATEGORY + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetAssetCategories.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[0] + keywords);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_ASSET_CATEGORY
                    + "!$" + prefix + "$" + (index + 1)
                    + ":$" + prefix + "$" + dtosLength);
        }
        return dtosLength;
    }


    private  Integer filledDataAssetCategoryView(Sheet sheetAssetCategories,
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
            String numberCodePattern=dtos.get(indexStart).getNumberCodePattern();
            row.createCell(0).setCellValue(numberCodePattern);
            row.createCell(1).setCellValue(valueCell);
            row.createCell(2).setCellValue(minimumTimeDepreciation);
            row.createCell(3).setCellValue(maximumTimeDepreciation);
            row.createCell(4).setCellValue(valueWearTear);
            row.createCell(5).setCellValue(yearWearTear);
            ++indexStart;
        }
        return dtosLength;
    }
}
