package com.example.csvccdshustbe.utility;

import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
public class FileUtil {

    public final static String EXT_PDF = "pdf";
    public final static String EXT_OFFICE = "xls,xlsx,doc,docx,ppt";
    public static final String FOLDER_NAME_PARENT = "resources";
    public static final String FOLDER_NAME_IMAGE = "upload_image";
    public static final String FOLDER_NAME_FILE = "upload_file";
    public static final String FOLDER_ASSET = "asset";
    public static final String FOLDER_NAME_REPORT ="report";
    public static final String FOLDER_NAME_SAMPLE_ASSET ="sample_asset";
    public static final String SEPARATOR = "/";
    public static String pathReturn = "";
    private static final StringBuilder builder = new StringBuilder();
    /**
     * Constant File
     * */
    public static final String[] FILE_ASSET = {"png", "jpg", "pdf", "xls","xlsm","xlsx", "docx", "doc"};


    // Save file if success then return file path, else return null
    public static Map<String, String> saveFiles(MultipartFile[] uploadedFile) {
        Map<String, String> outputList = new LinkedHashMap<>();
        for (MultipartFile multipartFile : uploadedFile) {
            if (multipartFile.isEmpty()) {
                return new LinkedHashMap<>();
            }
            String mimeType = multipartFile.getContentType().split("/")[0];
            if ("image".contains(mimeType)) {
                outputList.put(saveImage(multipartFile), multipartFile.getOriginalFilename());
            } else {
                outputList.put(saveFile(multipartFile), multipartFile.getOriginalFilename());
            }
        }
        return outputList;
    }

    // Save file if success then return file path, else return null
    public static String saveFile(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_FILE);
    }

    // Save file if success then return file path, else return null
    public static String saveImage(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_IMAGE);
    }

    private static String save(MultipartFile uploadedFile, String folderName) {
        try {
            return save(uploadedFile.getInputStream(), uploadedFile.getOriginalFilename(), folderName);
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    public static String getFolderInfo() {
        builder.setLength(0);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        builder.append(year).append(month).append(day);
        return builder.toString();
    }

    public static String getTimeInfo() {
        builder.setLength(0);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        builder.append(hour).append(minute).append(second);
        return builder.toString();
    }

    public static String save(InputStream inputStream, String fileName, String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = buildFolderUpload(folderName);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(fileName));
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.copyInputStreamToFile(inputStream, file);
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(fileName);
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }


    public static FileInputStream getInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException ignored) {

        }
        return null;
    }

    public static String getFilePathFromDatabase(String databaseFilePath) {
        return SEPARATOR + FOLDER_NAME_PARENT + SEPARATOR + databaseFilePath;
    }

    // Get only file name
    public static String getFilenameFromFilePath(String databaseFilePath) {
        if (StringUtils.isBlank(databaseFilePath)) {
            return "";
        }
        return databaseFilePath.substring(databaseFilePath.lastIndexOf(SEPARATOR) + 1);
    }

    // Create file id (unique)
    public static String generateFileId() {
        return DateUtil.getCurrentDateStr();
    }

    public static String buildFolderUpload(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String folderSave = PropertiesUtil.getProperty("vn.cpa.static.location.upload");
        pathReturn = "";
        pathReturn = pathReturn + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
        // in project
        return folderSave
                + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
    }


    public static String buildFolderUploadV2(String folderName){
        String todayFolder = DateUtil.getTodayFolder();
        String folderSave = PropertiesUtil.getProperty("vn.cpa.static.location");
        pathReturn = "";
        pathReturn = pathReturn + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
        // in project
        return folderSave
                + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
    }



    public static String getFileExtFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isPdfFileExt(String fileName) {
        return StringUtils.equalsIgnoreCase(getFileExtFromFileName(fileName), EXT_PDF);
    }

    public static boolean isOfficeFileExt(String fileName) {
        return StringUtils.contains(EXT_OFFICE, getFileExtFromFileName(fileName).toLowerCase());
    }

    public static boolean isAcceptFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types"));
    }

    public static boolean isAcceptAudioFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types_audio"));
    }


    public static boolean isAcceptFileTypeImage(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_image_file_types"));
    }

    public static boolean isAcceptFileType(String fileName, String acceptTypes) {
        if (StringUtils.isBlank(acceptTypes) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(acceptTypes.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName).toLowerCase());
    }

    // PDF
    public static boolean isAcceptFilePDFType(String fileName) {
        String fileTypeString = getAcceptFilePDFString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static String getAcceptFilePDFString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    public static void deleteFileByListPath(List<String> fileList) {
        for (String filePath : fileList) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static boolean checkNameEndFile(MultipartFile file, String[] checkNameFile) {
        String[] nameFile = file.getOriginalFilename().split("\\.");
        System.out.println(nameFile[1]);
        boolean isCheck = false;
        for (String endFileName : checkNameFile) {
            if (endFileName.equals(nameFile[1])) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }
    public static void validateFileUploadAsset(MultipartFile file) throws FileException {
        if (file.isEmpty()){
            throw new FileException("File is empty!");
        }
        if( Arrays.stream(FILE_ASSET).noneMatch(x->x.equals(FilenameUtils.getExtension(file.getOriginalFilename())))){
            throw new FileException("Validate extension file!");
        }
        if (checkSizeFile(file)){
            throw new FileException("Validate size file!");
        };
    }


    public static boolean checkSizeFile(MultipartFile file) {
        Long sizeFileByte = file.getSize();
        double sizeFileMb = (sizeFileByte) / Math.pow(1024, 2);
        return sizeFileMb <= Double.parseDouble(PropertiesUtil.getProperty("spring.servlet.multipart.max-file-size").substring(0, 2));
    }

    public static boolean checkSizeFileImage(MultipartFile file) {
        Long sizeFileByte = file.getSize();
        double sizeFileMb = (sizeFileByte) / Math.pow(1024, 2);
        return sizeFileMb <= Double.parseDouble(PropertiesUtil.getProperty("max-size-upload-image").substring(0, 2));
    }

    public static void checkFileAsset(MultipartFile file) throws FileException {
        if (file.isEmpty()){
            throw new FileException("File is empty!");
        }
        if( Arrays.stream(FILE_ASSET).noneMatch(x->x.equals(FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase()))){
            throw new FileException("Validate extension file!");
        }
        if (!FileUtil.checkSizeFileImage(file)){
            throw new FileException("Validate size file!");
        };
    }


    public static byte[] convertStringToByteWithBase64(String pathFilePhysical) {
        return Base64.getDecoder().decode(pathFilePhysical);
    }

    public static void writeData(byte[] bytes, String filePath) {
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFileSampleAsset(String nameFile){
        String root = PropertiesUtil.getProperty("hust.csvc.static.location.static.files");
        String random = RandomStringUtils.randomAlphanumeric(16);
        String fileFinal = root + File.separator + getFolderInfo() + File.separator + random + nameFile;
        File file = new File(fileFinal);
        if (!file.exists() && !file.mkdirs()) {
            log.error("Can't create folder");
        }
        return file;
    }


    public static String createFileSampleAsset(String fileExcelName, String nameClass){
        String root = PropertiesUtil.getProperty("hust.csvc.static.location.static.files") + File.separator + FOLDER_NAME_SAMPLE_ASSET;
        String random = RandomStringUtils.randomAlphanumeric(16);
        String filePathTemplate = root + File.separator + fileExcelName;
        File oldFile = new File(filePathTemplate);
        String rootUpload = PropertiesUtil.getProperty("vn.cpa.static.location.upload");
        String fileFinal = rootUpload + File.separator + FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + random + nameClass + fileExcelName;
        pathReturn = FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + random + nameClass + fileExcelName;
        File newFile = new File(fileFinal);
        try {
            FileUtils.copyFile(oldFile, newFile);
        } catch (IOException e) {
            log.error("Can't not copy file!");
            e.printStackTrace();
        }
        return fileFinal;
    }

    public static String createFileReportClassTime(String fileExcelName){
        String root = PropertiesUtil.getProperty("file_report_excel_template");
        String random = RandomStringUtils.randomAlphanumeric(20);
        String filePathTemplate = root + File.separator + fileExcelName;
        File oldFile = new File(filePathTemplate);
        String rootUpload = PropertiesUtil.getProperty("vn.cpa.static.location.upload");
        String fileFinal = rootUpload + File.separator + FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + random +  fileExcelName;
        pathReturn = FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + random +  fileExcelName;
        File newFile = new File(fileFinal);
        try {
            FileUtils.copyFile(oldFile, newFile);
        } catch (IOException e) {
            log.error("Can't not copy file!");
            e.printStackTrace();
        }
        return fileFinal;
    }



    public static String createFileReportCustomName(String fileExcelName, String name){
        String root = PropertiesUtil.getProperty("file_report_excel_template");
        String random = RandomStringUtils.randomAlphanumeric(20);
        String filePathTemplate = root + File.separator + fileExcelName;
        File oldFile = new File(filePathTemplate);
        String rootUpload = PropertiesUtil.getProperty("vn.cpa.static.location.upload");
        String fileFinal = rootUpload + File.separator + FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + name + "-" + getTimeInfo() + getFolderInfo() + "." + FilenameUtils.getExtension(fileExcelName);
        pathReturn = FOLDER_NAME_REPORT + File.separator + getFolderInfo() + File.separator + name + "-" + getTimeInfo() + getFolderInfo() + "." + FilenameUtils.getExtension(fileExcelName);
        File newFile = new File(fileFinal);
        try {
            FileUtils.copyFile(oldFile, newFile);
        } catch (IOException e) {
            log.error("Can't not copy file!");
            e.printStackTrace();
        }
        return fileFinal;
    }


}
