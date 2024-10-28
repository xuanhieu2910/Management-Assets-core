package com.example.csvccdshustbe.service.upload;

import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService { ;

     String saveAndReturnPathAsset(MultipartFile file, String folderName) throws IOException, FileException;

     void deleteByPathFile(String pathFile)
             throws ValidateFiledException, IOException, InterruptedException;
     Resource downLoadFileImportAsset() throws IOException;
     Resource downLoadReportByPathFile(String pathFile) throws IOException;

}
