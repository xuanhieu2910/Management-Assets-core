package com.example.csvccdshustbe.service.assetCategories;

import com.example.csvccdshustbe.dto.assetCategories.BluePrintAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetCategories.BluePrintParentAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesToDownloadDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetCategories.*;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesVisibleResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAssetCategoryDetailsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface AssetCategoriesService {

    List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked();

    Page<FindAllAssetCategoriesVisibleResponse>
    findAllAssetCategoriesVisibleByCodeAssetCategories(FindAllAssetCategoriesVisibleRequest request)
            throws ValidateFiledException;

    Page<FindAllAssetCategoriesVisibleResponse>
    findAllAssetCategoriesVisible(FindAllAssetCategoriesVisibleRequest request)
            throws ValidateFiledException;

    Page<FindAllAssetCategoriesResponse> findAllAssetCategories(FindAllDocumentAssetCategoriesRequest request);

    AssetCategories findAssetCategoriesVisibleByCodeName(String codeName) throws Exception;

    AssetCategories findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory, Integer visible);

    void createAssetCategory(CreateAssetCategoryRequest request) throws ValidateFiledException;
    void updateAssetCategory(UpdateAssetCategoryRequest request) throws ValidateFiledException;

    void deleteAssetCategoryByIdAssetCategory(Integer idAssetCategory) throws ValidateFiledException;

    BluePrintParentAssetCategoryDto findBluePrintParentAssetCategoryDtoById(Integer idParentAssetCategory);

    FindAssetCategoryDetailsResponse findAssetCategoryDetailsResponseByCode(String codeAssetCategory);

    void updateStatusAssetCategory(UpdateStatusAssetCategory statusAssetCategory) throws ValidateFiledException;

    Map<String, List<FindAllAssetCategoriesToDownloadDto>> findAllAssetCategoriesVisibleResponseToDownload();
}
