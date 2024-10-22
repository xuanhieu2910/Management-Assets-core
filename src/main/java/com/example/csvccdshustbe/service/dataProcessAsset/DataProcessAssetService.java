package com.example.csvccdshustbe.service.dataProcessAsset;

import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataProcessAssetService {

    List<DataProcessAsset> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    void createNewDataProcessAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException;
     Page<FindAllProcessAssetResponse> findAllProcessAsset(FindAllProcessAssetRequest request);
}
