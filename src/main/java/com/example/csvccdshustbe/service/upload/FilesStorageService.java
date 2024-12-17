package com.example.csvccdshustbe.service.upload;

import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import org.apache.catalina.util.Introspection;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService { ;

     String saveAndReturnPathAsset(MultipartFile file, String folderName) throws IOException, FileException;

     void deleteByPathFile(String pathFile, String originalFile, String destinationFile)
             throws ValidateFiledException, IOException, InterruptedException;
     String downLoadFileImportAsset() throws IOException;
     String downLoadReportByPathFile(String pathFile) throws IOException;
     String downloadInventoryReportByCodeDocument(FindAllAssetProcessRequest request) throws IOException;
     String downLoadRevaluationReport(Integer idAssetProcess, Integer status) throws IOException;

    String updateFilesAttached(MultipartFile[] files, String folderName) throws FileException, IOException;
}
