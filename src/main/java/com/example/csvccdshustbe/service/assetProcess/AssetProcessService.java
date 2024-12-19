package com.example.csvccdshustbe.service.assetProcess;

import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.fluctuatingSituationAsset.AssetsFluctuatingSituationAssetDto;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetProcess.*;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponseUpdateInventory;
import com.example.csvccdshustbe.response.assetProcess.FindAllAssetProcessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AssetProcessService {
    List<AssetProcess> saveListAssetProcess(List<AssetProcess> assetProcessList);
    Page<FindAllAssetProcessResponse> findAllAssetProcess(FindAllAssetProcessRequest request);
    AssetProcess findAssetProcessByIdProcess(Integer idProcess);
    List<AssetProcessDto> findAllAssetProcessByIdProcess(Integer idProcess);
    void updateListAssetProcessByIdProcess(UpdateAllAssetProcessRequest request, Integer idProcess);
    void updateFinishListAssetProcessByIdProcess(UpdateAllAssetProcessRequest request);
    List<AssetProcessDto> findResultAssetLotByIdProcessAndCalculatorIsIncreaseAndIsDecrease(Integer idProcess);
    Page<FindAllAssetProcessResponse> findAllAssetLotProcess(FindAllAssetProcessRequest request);
    Page<FindAllAssetProcessResponse> findAllAssetChildrenProcess(FindAllAssetProcessRequest request);
    Page<FindAllAssetResponseUpdateInventory> findAllAssetUpdateInventoryProcess(FindAllAssetProcessRequest request);
    Page<FindAllAssetResponseUpdateInventory> findAllAssetProcessLotUpdateInventoryProcess(FindAllAssetProcessRequest request);
    List<AssetProcess> createNewAssetNotDeclareWhenInventory(AssetNotDeclareWhenInventoryRequest request) throws JsonProcessingException, ValidateFiledException;
    AssetProcess updateAssetProcessInventory(UpdateAssetProcessRequest request) throws JsonProcessingException, ValidateFiledException, IllegalAccessException;
    AssetProcess findAssetProcessByIdAssetProcess(Integer idAssetProcess);
    List<AssetsFluctuatingSituationAssetDto> findAssetsToFluctuatingSituationByIdProcess(Integer idProcess);
}
