package com.example.csvccdshustbe.service.assetCategories.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepository;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
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
public class AssetCategoriesImpl implements AssetCategoriesService {


    @Autowired
    AssetCategoriesRepository assetCategoriesRepository;


    @Override
    public List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked() {
        List<AssetCategories> categories = assetCategoriesRepository.findAllAssetCategoriesIsPickedAndVisible();
        return convertToFindAllAssetCategoriesPicked(categories);
    }

    @Override
    public Page<FindAllAssetCategoriesResponse> findAllAssetCategoriesByCodeNameAndVisible(
            FindAllAssetCategoriesRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllAssetCategoriesByCodeAndVisibleDto> categories =
                assetCategoriesRepository.findAllAssetCategoriesByCodeAndVisible(pageable, request);
        return new PageImpl<>(convertToFindAllAssetCategoriesByCodeAndVisible(categories.get().collect(Collectors.toList())), pageable, categories.getTotalElements());
    }

    private List<FindAllAssetCategoriesResponse> convertToFindAllAssetCategoriesByCodeAndVisible
            (List<FindAllAssetCategoriesByCodeAndVisibleDto> collect) {
        List<FindAllAssetCategoriesResponse> responses = new ArrayList<>();
        for (FindAllAssetCategoriesByCodeAndVisibleDto categorie : collect){
            FindAllAssetCategoriesResponse response = new FindAllAssetCategoriesResponse();
            response.setIdAssetCategory(categorie.getIdAssetCategory());
            response.setName(categorie.getName());
            response.setCodeName(categorie.getCodeName());
            response.setDepth(categorie.getDepth());
            response.setPath(categorie.getPath());
            response.setParent(categorie.getParent());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetCategoriesPickedResponse> convertToFindAllAssetCategoriesPicked(List<AssetCategories> categories) {
        List<FindAllAssetCategoriesPickedResponse> responses = new ArrayList<>();
        for (AssetCategories ass: categories){
            FindAllAssetCategoriesPickedResponse res = new FindAllAssetCategoriesPickedResponse();
            res.setName(ass.getName());
            res.setCodeName(ass.getCodeName());
            res.setPathImage(ass.getPathImage());
            responses.add(res);
        }
        return responses;
    }
}
