package com.example.csvccdshustbe.service.original.methodBuyAsset;

import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.response.methodBuyAsset.FindAllMethodBuyAssetResponse;
import org.springframework.data.domain.Page;

public interface MethodBuyAssetService {

    Page<FindAllMethodBuyAssetResponse> findAllActiveMethodBuyAssetResponse(FindAllMethodBuyAssetPickedRequest request);

}
