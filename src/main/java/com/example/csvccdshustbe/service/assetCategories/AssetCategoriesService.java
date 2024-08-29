package com.example.csvccdshustbe.service.assetCategories;

import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetCategoriesService {

    List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked();

    Page<FindAllAssetCategoriesResponse> findAllAssetCategoriesByCodeNameAndVisible(FindAllAssetCategoriesRequest request);
}
