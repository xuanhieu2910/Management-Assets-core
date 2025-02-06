package com.example.csvccdshustbe.service.asset;

import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.*;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.response.asset.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AssetService {

    Asset storeAsset(Asset asset);
    void createAsset(Map<String,Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;
    void createAssetLot(Map<String,Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;
    Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request);
    Page<FindAllAssetLotChildrenResponse> findAllAssetLotChildren(FindAllAssetLotChildrenRequest request);
    FindDetailsAssetResponse findDetailsAssetBySaltAsset(String saltAsset) throws ValidateFiledException, IllegalAccessException;
    void updateAsset(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException, IllegalAccessException;
    void updateAssetLot(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException, IllegalAccessException;
    void deleteAssetBySaltAsset(String saltAsset) throws ValidateFiledException;
    void deleteAssetBySaltAssetLot(String saltAsset) throws ValidateFiledException;
    Page<FindAllGroundAssetResponse> findAllGroundAsset(FindAllGroundAssetRequest request);
    String uploadFile(MultipartFile multipartFile) throws FileException, IOException, FileExcelException;
    void deleteFile(String pathFile, String originalFile, String destinationFile) throws ValidateFiledException, IOException, InterruptedException;
    String downloadFileTemplateImportAsset() throws IOException;
    String exportFileReportByPath(String pathFile) throws IOException;
    void uploadFileImportAsset(MultipartFile file) throws FileExcelException, ValidateFiledException, JsonProcessingException;
    Page<FindAllAssetResponseToIncrease> findAllAssetToIncrease(FinaAllAssetToIncreaseRequest request);
    Page<FindAllAssetResponseToIncrease> findAllAssetChildrenToIncrease(FinaAllAssetToIncreaseRequest request);
    String generateCodeAsset(String prefix);
    Page<FindAllAssetResponseToInventory> findAllAssetToInventory(FindAllAssetToInventoryRequest inventoryRequest);
    void createAssetFromFile(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;
    void updateInformationProcessCurrentAsset(List<Integer> idsAsset, Process process);
    Integer countAssetIncreasedNotDecreasedOrNotPending(List<Integer> idsAsset);
    Integer countAssetByIdsAssetAndNotIncreasedOrDecreasedOrPending(List<Integer> idsAsset);
    void updateAssetStatusProcessCurrentByIdProcessCurrent(Integer idProcessCurrent, Integer statusProcessCurrent);
    void updateAssetStatusProcessCurrentAndIsIncreaseAndIsDecrease(Integer idProcess, Integer status, String typeProcess);
    Page<FindAllAssetResponseToChange> findAllAssetToChange(FindAllAssetToChangeRequest changeRequest);
    Page<FindAllAssetResponseToRevaluation> findAllAssetToRevaluation(FindAllAssetToRevaluationRequest changeRequest);
    Page<FindAllAssetResponseToRevaluation> findAllAssetChildrenToRevaluation(FindAllAssetToRevaluationRequest changeRequest);
    Page<FindAllAssetResponseToDecrease> findAllAssetToDecrease(FindAllAssetToDecreaseRequest decreaseRequest);
    Asset duplicationAssetBySaltAsset(String saltAssetRoot) throws ValidateFiledException, IllegalAccessException;
    void updateInformationAssetByProcess(Process process, Integer status) throws JsonProcessingException, ValidateFiledException, IllegalAccessException;
    void updateIncreaseOrDecreaseAssetLotByIdProcess(Integer idProcess, String typeProcess);
    List<Asset> findAllAssetByIdsAsset(List<Integer> idsAsset);
    Page<FindAllAssetResponseToDecrease> findAllAssetChildrenToDecrease(FindAllAssetToDecreaseRequest decreaseRequest);
    Page<FindAllAssetChildrenToInventoryResponse> findAllAssetChildrenToInventory(FindAllAssetToInventoryRequest inventoryRequest);
    Asset findAssetByIdAsset(Integer idAsset);
    StatisticsAssetFindAllResponse getStatisticFindAllAsset();

}
