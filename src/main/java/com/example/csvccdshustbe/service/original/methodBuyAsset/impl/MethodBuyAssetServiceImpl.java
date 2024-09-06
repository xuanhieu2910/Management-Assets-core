package com.example.csvccdshustbe.service.original.methodBuyAsset.impl;

import com.example.csvccdshustbe.entity.MethodBuyAsset;
import com.example.csvccdshustbe.repository.methodBuyAsset.MethodBuyAssetRepository;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.response.methodBuyAsset.FindAllMethodBuyAssetResponse;
import com.example.csvccdshustbe.service.original.methodBuyAsset.MethodBuyAssetService;
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
public class MethodBuyAssetServiceImpl implements MethodBuyAssetService {

    @Autowired
    MethodBuyAssetRepository methodBuyAssetRepository;

    @Override
    public Page<FindAllMethodBuyAssetResponse> findAllActiveMethodBuyAssetResponse(FindAllMethodBuyAssetPickedRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<MethodBuyAsset> methodBuyAssets = methodBuyAssetRepository.findAllActiveMethodBuyAsset(request, pageable);
        return new PageImpl<>(convertToFindAllMethodBuyAssetResponse(methodBuyAssets.get().collect(Collectors.toList())),
                pageable, methodBuyAssets.getTotalElements());
    }

    private List<FindAllMethodBuyAssetResponse> convertToFindAllMethodBuyAssetResponse(List<MethodBuyAsset> collect) {
        List<FindAllMethodBuyAssetResponse> responses = new ArrayList<>();
        for (MethodBuyAsset methodBuyAsset : collect){
            FindAllMethodBuyAssetResponse response = new FindAllMethodBuyAssetResponse();
            response.setIdMethodBuyAsset(methodBuyAsset.getIdMethodBuyAsset());
            response.setName(methodBuyAsset.getTitle());
            responses.add(response);
        }
        return responses;
    }
}
