package com.example.csvccdshustbe.utility;

import com.example.csvccdshustbe.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public class FilesUtils {

    public static void validateFile(MultipartFile file) throws FileException {
        if (file.isEmpty()){
            throw new FileException("File is empty!");
        }

    }
}
