package com.example.csvccdshustbe.service.assetCategories;

import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetCategories.CreateAssetCategoryRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import com.example.csvccdshustbe.request.assetCategories.UpdateAssetCategoryRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetCategoriesService {

    List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked();

    Page<FindAllAssetCategoriesResponse> findAllAssetCategoriesByCodeNameAndVisible(FindAllAssetCategoriesRequest request);

    AssetCategories findAssetCategoriesVisibleByCodeName(String codeName) throws Exception;

    AssetCategories findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory, Integer visible);

    void createAssetCategory(CreateAssetCategoryRequest request) throws ValidateFiledException;
    void updateAssetCategory(UpdateAssetCategoryRequest request) throws ValidateFiledException;

    void deleteAssetCategoryByIdAssetCategory(Integer idAssetCategory);
}
