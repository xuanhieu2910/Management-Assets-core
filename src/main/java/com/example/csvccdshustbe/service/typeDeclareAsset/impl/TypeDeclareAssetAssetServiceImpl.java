package com.example.csvccdshustbe.service.typeDeclareAsset.impl;

import com.example.csvccdshustbe.entity.TypeDeclareAsset;
import com.example.csvccdshustbe.repository.typeDeclareAsset.TypeDeclareAssetRepository;
import com.example.csvccdshustbe.request.typeDeclareAsset.FindAllTypeDeclareAssetActiveRequest;
import com.example.csvccdshustbe.response.typeDeclareAsset.FindAllTypeDeclareAssetResponse;
import com.example.csvccdshustbe.service.typeDeclareAsset.TypeDeclareAssetService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeDeclareAssetAssetServiceImpl implements TypeDeclareAssetService {


    @Autowired
    TypeDeclareAssetRepository typeDeclareAssetRepository;

    @Override
    public Page<FindAllTypeDeclareAssetResponse> findAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<TypeDeclareAsset> typeDeclareAssets = typeDeclareAssetRepository.findAllTypeDeclareAssetActive(request, pageable);
        return new PageImpl<>(convertToFindAllTypeDeclareAsset(typeDeclareAssets.get().collect(Collectors.toList())),
                pageable,typeDeclareAssets.getTotalElements());
    }

    private List<FindAllTypeDeclareAssetResponse> convertToFindAllTypeDeclareAsset(List<TypeDeclareAsset> collect) {
        List<FindAllTypeDeclareAssetResponse> responses = new ArrayList<>();
        for(TypeDeclareAsset typeDeclareAsset: collect){
            FindAllTypeDeclareAssetResponse response = new FindAllTypeDeclareAssetResponse();
            response.setIdTypeDeclareAsset(typeDeclareAsset.getIdTypeDeclareAsset());
            response.setName(typeDeclareAsset.getName());
            responses.add(response);
        }
        return responses;
    }
}
