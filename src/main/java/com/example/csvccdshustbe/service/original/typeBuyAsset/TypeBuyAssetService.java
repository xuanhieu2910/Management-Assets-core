package com.example.csvccdshustbe.service.original.typeBuyAsset;

import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.typeBuyAsset.FindAllTypeBuyAssetPickedRequest;
import com.example.csvccdshustbe.response.typeBuyAsset.FindAllTypeBuyAssetPickedResponse;
import org.springframework.data.domain.Page;

public interface TypeBuyAssetService {

    Page<FindAllTypeBuyAssetPickedResponse> findAllTypeBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request);

}
