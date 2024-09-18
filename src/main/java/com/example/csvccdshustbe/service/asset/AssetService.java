package com.example.csvccdshustbe.service.asset;

import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponse;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import com.example.csvccdshustbe.response.asset.FindDetailsAssetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface AssetService {

    void createAsset(Map<String,Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;

    Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request);


    FindDetailsAssetResponse findDetailsAssetByCodeAsset(String codeAsset) throws ValidateFiledException, IllegalAccessException;


    void updateAsset(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException, IllegalAccessException;

    void deleteAssetByCodeAsset(String codeAsset) throws ValidateFiledException;

    Page<FindAllGroundAssetResponse> findAllGroundAsset(FindAllGroundAssetRequest request);

    String uploadFile(MultipartFile multipartFile) throws FileException, IOException, FileExcelException;

    void deleteFile(String pathFile) throws ValidateFiledException, IOException, InterruptedException;

    Resource downloadFileTemplateImportAsset();
}
