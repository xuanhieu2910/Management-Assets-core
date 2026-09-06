package com.example.csvccdshustbe.service.original.typeBuyAsset.impl;

import com.example.csvccdshustbe.entity.TypeBuyAsset;
import com.example.csvccdshustbe.repository.typeBuyAsset.TypeBuyAssetRepository;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.typeBuyAsset.FindAllTypeBuyAssetPickedRequest;
import com.example.csvccdshustbe.response.typeBuyAsset.FindAllTypeBuyAssetPickedResponse;
import com.example.csvccdshustbe.service.original.typeBuyAsset.TypeBuyAssetService;
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
public class TypeBuyAssetServiceImpl implements TypeBuyAssetService {


    @Autowired
    TypeBuyAssetRepository typeBuyAssetRepository;

    @Override
    public Page<FindAllTypeBuyAssetPickedResponse> findAllTypeBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<TypeBuyAsset> typeBuyAssets = typeBuyAssetRepository.findAllBuyAssetPicked(request, pageable);
        return new PageImpl<>(convertToFindAllTypeBuyAssetPickedReponse(typeBuyAssets.get().collect(Collectors.toList())),
                pageable, typeBuyAssets.getTotalElements());
    }

    private List<FindAllTypeBuyAssetPickedResponse> convertToFindAllTypeBuyAssetPickedReponse(List<TypeBuyAsset> collect) {
        List<FindAllTypeBuyAssetPickedResponse> responses = new ArrayList<>();
        for (TypeBuyAsset typeBuyAsset : collect){
            FindAllTypeBuyAssetPickedResponse response = new FindAllTypeBuyAssetPickedResponse();
            response.setIdTypeBuyAsset(typeBuyAsset.getIdTypeBuyAsset());
            response.setName(typeBuyAsset.getTitle());
            responses.add(response);
        }
        return responses;
    }
}
