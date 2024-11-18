package com.example.csvccdshustbe.repository.assetInstance;

import com.example.csvccdshustbe.entity.AssetInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetInstanceRepositoryCustom {

    Page<AssetInstance> findAllAssetInstance(Pageable pageable);

    List<AssetInstance> findAllAssetInstanceByIds(List<Integer> idsAssetInstance);

    long totalError();

}
