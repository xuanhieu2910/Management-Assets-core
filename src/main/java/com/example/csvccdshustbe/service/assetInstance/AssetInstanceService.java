package com.example.csvccdshustbe.service.assetInstance;

import com.example.csvccdshustbe.request.assetInstance.DeleteAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.UpdateAssetInstanceRequest;
import com.example.csvccdshustbe.response.assetInstance.FindAllAssetInstanceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetInstanceService {

    Page<FindAllAssetInstanceResponse> findAllAssetInstance(FindAllAssetInstanceRequest request);
    void updateAssetInstance(UpdateAssetInstanceRequest request);
    void deleteAssetInstance(DeleteAssetInstanceRequest request);
}
