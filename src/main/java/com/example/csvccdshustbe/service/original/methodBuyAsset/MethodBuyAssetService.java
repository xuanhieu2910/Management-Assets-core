package com.example.csvccdshustbe.service.original.methodBuyAsset;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.methodBuyAsset.CreateMethodBuyAssetRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.UpdateMethodBuyAssetRequest;
import com.example.csvccdshustbe.response.methodBuyAsset.FindAllMethodBuyAssetResponse;
import org.springframework.data.domain.Page;

public interface MethodBuyAssetService {

    Page<FindAllMethodBuyAssetResponse> findAllActiveMethodBuyAssetResponse(FindAllMethodBuyAssetPickedRequest request);

    void createMethodBuyAssetService(CreateMethodBuyAssetRequest request) throws ValidateFiledException;

    void updateMethodBuyAssetService(UpdateMethodBuyAssetRequest request) throws ValidateFiledException;

    void deleteMethodBuyAssetService(Integer idMethodBuyAsset);


}
