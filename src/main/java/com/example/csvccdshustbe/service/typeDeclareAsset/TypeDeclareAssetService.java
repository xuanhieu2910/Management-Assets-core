package com.example.csvccdshustbe.service.typeDeclareAsset;

import com.example.csvccdshustbe.request.typeDeclareAsset.FindAllTypeDeclareAssetActiveRequest;
import com.example.csvccdshustbe.response.typeDeclareAsset.FindAllTypeDeclareAssetResponse;
import org.springframework.data.domain.Page;

public interface TypeDeclareAssetService {

    Page<FindAllTypeDeclareAssetResponse> findAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request);

}
