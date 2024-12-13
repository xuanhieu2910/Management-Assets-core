package com.example.csvccdshustbe.repository.asset;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllGroundAssetDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetParentToInventoryDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.request.asset.*;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface AssetRepositoryCustom {

    Page<FindAllAssetDto> findAllAssetDtoByIdsDepartment(FindAllAssetRequest request, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetLotChildrenDtoByIdsDepartment(FindAllAssetLotChildrenRequest request, Pageable pageable);
    Optional<AssetBluePrintDto> findDetailAssetBySaltAsset(String saltAsset);
    Optional<Asset> findAssetBySalt(String salt);
    void deleteByIdAsset(Integer idAsset);
    Page<FindAllGroundAssetResponse> findAllGroundAsset(Pageable pageable, FindAllGroundAssetRequest request);
    List<FindAllGroundAssetDto> findAllGroundAssetToDownload();
    Page<FindAllAssetDto> findAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetChildrenDtoToIncrease(FinaAllAssetToIncreaseRequest request, Pageable pageable);
    Optional<Asset> findAssetByIdDepartmentOrigin(Integer idDepartmentOrigin);
    Optional<Asset> findAssetLotByIdDepartmentOrigin(Integer idDepartmentOrigin);
    List<Asset> findAllAssetChildrenByParentId(Integer idAsset);
    Page<FindAllAssetParentToInventoryDto> findAllAssetDtoToInventory(FindAllAssetToInventoryRequest inventoryRequest, Pageable pageable);
    List<Asset> findAllAssetByIdsAsset(List<Integer> idsAsset);
    Optional<Asset> findAllAssetByIdAsset(Integer idAsset);
    Integer countAssetIncreasedNotDecreasedByIdsAssetOrPending(List<Integer> idsAsset);
    Integer countAssetByIdsAssetAndNotIncreaseOrDecreasedOrPending(List<Integer> idsAsset);
    void updateAssetStatusProcessCurrentByIdProcessCurrent(Integer idProcessCurrent, Integer statusProcessCurrent);
    void updateAssetStatusProcessCurrentAndIsIncrease(Integer idProcess, Integer status, Integer isIncrease);
    void updateAssetStatusProcessCurrentAndIsDecrease(Integer idProcess, Integer status, Integer isDecrease);
    Page<FindAllAssetDto> findAllAssetDtoToChange(FindAllAssetToChangeRequest request, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetDtoToRevaluation(FindAllAssetToRevaluationRequest revaluationRequest, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetDtoToDecrease(FindAllAssetToDecreaseRequest decreaseRequest, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetChildrenDtoToDecrease(FindAllAssetToDecreaseRequest decreaseRequest, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetChildrenDtoToInventory(FindAllAssetToInventoryRequest inventoryRequest, Pageable pageable);
    Page<FindAllAssetDto> findAllAssetChildrenDtoToRevaluation(FindAllAssetToRevaluationRequest revaluationRequest, Pageable pageable);
}
