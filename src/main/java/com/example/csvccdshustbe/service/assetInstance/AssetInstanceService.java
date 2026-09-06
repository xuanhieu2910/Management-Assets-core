package com.example.csvccdshustbe.service.assetInstance;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetInstance.CreateAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.DeleteAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.UpdateAssetInstanceRequest;
import com.example.csvccdshustbe.response.assetInstance.FindAllAssetInstanceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetInstanceService {

    Page<FindAllAssetInstanceResponse> findAllAssetInstance(FindAllAssetInstanceRequest request);
    void updateAssetInstance(UpdateAssetInstanceRequest request);
    void deleteAssetInstance(DeleteAssetInstanceRequest request);
    void createAssetInstance(CreateAssetInstanceRequest request) throws ValidateFiledException, JsonProcessingException;
}
