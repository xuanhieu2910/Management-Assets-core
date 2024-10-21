package com.example.csvccdshustbe.repository.dataProcessAsset;

import com.example.csvccdshustbe.entity.DataProcessAsset;

import java.util.List;

public interface DataProcessAssetRepositoryCustom {
    List<DataProcessAsset> findDataProcessAssetByIdsAsset(List<Integer> idsAsset);

}
