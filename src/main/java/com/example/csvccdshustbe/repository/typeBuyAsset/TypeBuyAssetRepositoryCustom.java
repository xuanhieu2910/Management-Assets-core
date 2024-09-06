package com.example.csvccdshustbe.repository.typeBuyAsset;

import com.example.csvccdshustbe.entity.TypeBuyAsset;
import com.example.csvccdshustbe.request.typeBuyAsset.FindAllTypeBuyAssetPickedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeBuyAssetRepositoryCustom {

    Page<TypeBuyAsset> findAllBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request, Pageable pageable);

}
