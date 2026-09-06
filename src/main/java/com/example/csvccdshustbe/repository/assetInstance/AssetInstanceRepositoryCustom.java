package com.example.csvccdshustbe.repository.assetInstance;

import com.example.csvccdshustbe.entity.AssetInstance;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetInstanceRepositoryCustom {

    Page<AssetInstance> findAllAssetInstance(FindAllAssetInstanceRequest request, Pageable pageable);

    List<AssetInstance> findAllAssetInstanceByIds(List<Integer> idsAssetInstance);

    long totalError();

}
