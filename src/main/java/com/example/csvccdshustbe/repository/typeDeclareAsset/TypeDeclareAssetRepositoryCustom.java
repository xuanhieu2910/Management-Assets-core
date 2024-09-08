package com.example.csvccdshustbe.repository.typeDeclareAsset;

import com.example.csvccdshustbe.entity.TypeDeclareAsset;
import com.example.csvccdshustbe.request.typeDeclareAsset.FindAllTypeDeclareAssetActiveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeDeclareAssetRepositoryCustom {

    Page<TypeDeclareAsset> findAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request, Pageable pageable);

}
