package com.example.csvccdshustbe.repository.asset;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllGroundAssetDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.request.asset.FinaAllAssetToIncreaseRequest;
import com.example.csvccdshustbe.request.asset.FindAllAssetDocumentRequest;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssetRepositoryCustom {

    Page<FindAllAssetDto> findAllAssetDtoByIdsDepartment(FindAllAssetRequest request, Pageable pageable);

    Optional<AssetBluePrintDto> findDetailAssetByCodeAsset(String codeAsset);

    Optional<Asset> findAssetByCodeAsset(String codeAsset);

    void deleteByIdAsset(Integer idAsset);

    Page<FindAllGroundAssetResponse> findAllGroundAsset(Pageable pageable, FindAllGroundAssetRequest request);

    List<FindAllGroundAssetDto> findAllGroundAssetToDownload();

    Page<FindAllAssetDto> findAllAssetDtoToIncrease(FinaAllAssetToIncreaseRequest request, Pageable pageable);

    Page<FindAllAssetDto> findAllAssetDocumentByCodeDocument(FindAllAssetDocumentRequest request, Pageable pageable);

    Optional<Asset> findAssetByIdDepartmentOrigin(Integer idDepartmentOrigin);
    Optional<Asset> findAssetLotByIdDepartmentOrigin(Integer idDepartmentOrigin);
}
