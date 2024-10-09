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
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.districts.DistrictsService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.goalsUseGround.GoalsUseGroundService;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
import com.example.csvccdshustbe.service.original.OriginalService;
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
    private static final String NAME_SHEET_DATA_LOCATION = "Location";
    private static final String NAME_SHEET_DATA_UNITS = "Units";
    private static final String NAME_SHEET_DATA_DOCUMENT_ATTACK = "DocumentAttacks";
    private static final String NAME_SHEET_DATA_PROJECTS = "Projects";
    private static final String NAME_SHEET_DATA_PROVINCES = "Provinces";
    private static final String NAME_INDIRECT = "INDIRECT";
    private static final String VLOOKUP = "VLOOKUP";
    private static final Integer TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW  = 3;
    private static final Integer TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW  = 2000;
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
        String fileExcel = "C:\\Users\\hieux\\Desktop\\Projects\\src\\main\\resources\\static\\ABC.xlsx";
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


        
        String filePathOutput = "C:\\Users\\hieux\\Desktop\\DEF.xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePathOutput)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        workbook.close();
        return null;
    }

    private void createDataGoalsUseGround(Workbook workbook, List<FindAllGoalsUseGroundDto> dataGoalsUseGround) {
    }

    private void createDataMedicineGroup(Workbook workbook, List<MedicineGroupDetailsDto> dataMedicineGroup) {
    }

    private void createDataMedicineType(Workbook workbook, List<MedicineTypeDetailsDto> dataMedicineType) {
    }

    private void createPositionName(Workbook workbook, List<FindAllPositionNameDto> dataPositionName) {
    }

    private void createTypeUse(Workbook workbook, List<FindAllTypeUseDto> dataTypeUse) {
    }

    private void createGroundAsset(Workbook workbook, List<FindAllGroundAssetDto> dataGroundAsset) {
    }

    private void createUserUsed(Workbook workbook, Map<String, List<FindAllUserUsedDto>> dataUserUsed) {
    }

    private void createCountryProducer(Workbook workbook, List<CountryProducer> dataCountryProducer) {
    }

    private void createOriginal(Workbook workbook, Map<String, List<FindAllOriginalDto>> dataOriginal) {
    }

    private void createWards(Workbook workbook, Map<String, List<WardsDto>> dataWards) {
    }

    private void createAssetDepartment(Workbook workbook, List<FindAllDepartmentSDto> dataAssetDepartment) {

    }

    private void createDistrict(Workbook workbook, Map<String, List<DistrictsDto>> dataDistrict) {

    }

    private void createProvinces(Workbook workbook, List<ProvincesDto> dataProvinces) {

    }

    private void createProjects(Workbook workbook, List<FindAllProjectsDto> dataProjects) {
        Sheet sheetDocumentAttack = workbook.createSheet(NAME_SHEET_DATA_PROJECTS);
        Row row = null;
        int indexCell = 0;
        for (int i = 0; i< dataProjects.size(); i++){
            if (sheetDocumentAttack.getRow(i) == null) {
                row = sheetDocumentAttack.createRow(i);
            } else {
                row = sheetDocumentAttack.getRow(i);
            }
            String valueCell = dataProjects.get(i).getIdProject() + "." + dataProjects.get(i).getShortName();
            row.createCell(indexCell).setCellValue(valueCell);
        }
        CellReference cellReference = new CellReference(row.getCell(indexCell));
        String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_PROJECTS + "!", "").replaceAll("\\d","");
        String formula = "=" + NAME_SHEET_DATA_PROJECTS + "!$" + prefix + "$1:" + "$" + prefix + dataProjects.size();
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);

        CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 7, 7);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
    }

    private void createDocumentAttack(Workbook workbook, Map<String, List<FindAllLocationDto>> dataDepartment,
                                      Map<String, List<FindAllDocumentAttackDto>> dataDocumentAttack) {
        Sheet sheetDocumentAttack = workbook.createSheet(NAME_SHEET_DATA_DOCUMENT_ATTACK);
        Iterator<String> keywords = dataDepartment.keySet().iterator();
        int index = 0;
        while (keywords.hasNext()){
            String keyword = keywords.next();
            if (!dataDocumentAttack.containsKey(keyword)) {
                filledDataDocumentAttackDefault(sheetDocumentAttack,dataDocumentAttack.get("STT_100Macdinh"), index, keyword);
            } else {
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
        productValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
    }

    private void filledDataDocumentAttackDefault(Sheet sheetDocumentAttack, List<FindAllDocumentAttackDto> dtos,
                                                 int index, String keyword) {
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
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DOCUMENT_ATTACK + "!", "").replaceAll("\\d","");
            Name electronicsRange = sheetDocumentAttack.getWorkbook().createName();
            electronicsRange.setNameName(PREFIX[4] + keyword);
            electronicsRange.setRefersToFormula(NAME_SHEET_DATA_DOCUMENT_ATTACK
                    + "!$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1)
                    + ":$" + prefix + "$" + dtos.size());
        }
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
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_DOCUMENT_ATTACK + "!", "").replaceAll("\\d","");
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

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        String formula = NAME_INDIRECT + "(\"" + PREFIX[1] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 5,5);
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
            String prefix = cellReference.formatAsString().replaceAll(NAME_SHEET_DATA_UNITS + "!", "").replaceAll("\\d","");
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
        productValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        productValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        productValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        productValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(productValidation);
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
//        String formula = "=Department!$A$1:$A$20"
        String formula = "=" + NAME_SHEET_DATA_DEPARTMENT + "!$" + prefix + "$1:" + "$" + prefix + departments.length;
        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 3, 3);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);
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
                String valueCell = dtos.get(i).getIdLocation() + "." + dtos.get(i).getName();
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
                        + ":$" + prefix + "$" + (INDEX_START_FILLED_DATA + dtos.size()));
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
                        + ":$" + prefix + "$" + (INDEX_START_FILLED_DATA + 1 + dtos.size()));
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

        DataValidationHelper dvHelper = workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).getDataValidationHelper();
        DataValidationConstraint categoryConstraint = dvHelper.createExplicitListConstraint(assetCategories);
        CellRangeAddressList categoryAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 0, 0);
        DataValidation categoryValidation = dvHelper.createValidation(categoryConstraint, categoryAddressList);
        categoryValidation.setShowErrorBox(true);
        categoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        categoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        categoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        categoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(categoryValidation);

        String formula = NAME_INDIRECT + "(\"" + PREFIX[0] + "\"" + " & $A4)";
        DataValidationConstraint productConstraint = dvHelper.createFormulaListConstraint(formula);
        CellRangeAddressList productAddressList = new CellRangeAddressList(TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW,
                TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW, 1,1);
        DataValidation subCategoryValidation = dvHelper.createValidation(productConstraint, productAddressList);
        subCategoryValidation.setShowErrorBox(true);
        subCategoryValidation.createErrorBox(ERROR, "Custom text not allowed, please select from the drop-down list.");
        subCategoryValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        subCategoryValidation.createPromptBox(PROMPT, "Please click the drop-down item.");
        subCategoryValidation.setShowPromptBox(true);
        workbook.getSheet(NAME_SHEET_IMPORT_ASSET_CATEGORY).addValidationData(subCategoryValidation);

        // SET DEPRECIATION
        for (int rowIndex = TEMPLATE_IMPORT_ASSET_INDEX_FIRST_ROW; rowIndex <= TEMPLATE_IMPORT_ASSET_LIMIT_AMOUNT_ROW; rowIndex++) {
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
