package com.example.csvccdshustbe.repository.assetProcess;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.fluctuatingSituationAsset.AssetsFluctuatingSituationAssetDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetLotParentToUpdateInventoryDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetParentToUpdateInventoryDto;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssetProcessRepositoryCustom {
    Page<FindAllAssetDto> findAllAssetProcess(FindAllAssetProcessRequest request, Pageable pageable);
    Optional<AssetProcess> findAssetProcessByIdProcess(Integer idProcess);
    List<AssetProcessDto> findAssetProcessDtoByIdProcess(Integer idProcess);
    List<AssetProcess> findAssetProcessListByIdsAssetAndIdProcess(List<Integer> idsAsset, Integer idProcess);
    List<AssetProcess> findAllAssetProcessListByIdProcess(Integer idProcess);
    List<AssetProcessDto> findResultAssetLotByIdProcessAndCalculatorIsIncreaseAndIsDecrease(Integer idProcess);
    Page<FindAllAssetDto> findAllAssetLotProcess(FindAllAssetProcessRequest request, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetChildrenProcess(FindAllAssetProcessRequest request, Pageable pageable);
    Page<FindAllAssetParentToUpdateInventoryDto> findALlAssetProcessToUpdateInventory(FindAllAssetProcessRequest request,
                                                                                Pageable pageable);
    Page<FindAllAssetLotParentToUpdateInventoryDto> findALlAssetProcessLotToUpdateInventory(FindAllAssetProcessRequest request,
                                                                                            Pageable pageable);
    Optional<AssetProcess> findAssetProcessByIdAssetProcess(Integer idAssetProcess);
    List<AssetsFluctuatingSituationAssetDto> findAssetsToFluctuatingSituationByIdProcess(Integer idProcess);
}
