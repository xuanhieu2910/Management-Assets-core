package com.example.csvccdshustbe.service.dataProcessAsset;

import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;

import java.util.List;

public interface DataProcessAssetService {

    List<DataProcessAsset> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    void createNewDataProcessAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException;
}
