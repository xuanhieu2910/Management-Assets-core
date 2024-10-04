package com.example.csvccdshustbe.repository.assetCategories;

import com.example.csvccdshustbe.dto.assetCategories.BluePrintParentAssetCategoryDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesPickedDto;
import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoryDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesByCodeRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllDocumentAssetCategoriesRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAssetCategoryDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssetCategoriesRepositoryCustom {

    List<FindAllAssetCategoriesPickedDto> findAllAssetCategoriesIsPickedAndVisible();
    Page<FindAllAssetCategoriesByCodeAndVisibleDto>
    findAllAssetCategoriesByCodeAndVisible(Pageable pageable, FindAllAssetCategoriesByCodeRequest request);
    Page<FindAllAssetCategoryDto>
    findAllAssetCategories(Pageable pageable, FindAllDocumentAssetCategoriesRequest request);
    Optional<AssetCategories> findAssetCategoriesVisibleByCodeName(String codeName);
    Optional<AssetCategories> findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory, Integer visible);
    boolean checkAssetCategoriesByParentIdAndName(Integer parentId, String name);
    Optional<AssetCategories> findAssetCategoryParentByParentId(Integer parentId);
    Optional<AssetCategories> findAssetCategoryById(Integer idAssetCategory);
    boolean checkExitsAssetCategoriesByNameOrShortName(String name, String shortName);
    Optional<BluePrintParentAssetCategoryDto> findBluePrintAssetCategoryDtoById(Integer idAssetCategory);
    Optional<FindAssetCategoryDetailsResponse> findAssetCategoryDetailsPickedResponseByCode(String code);
    boolean isCheckExitsAssetByIdAssetCategory(Integer idAssetCategory);
    Map<String, List<FindAllAssetCategoriesByCodeAndVisibleDto>> findAllAssetCategoriesByVisibleToDownload();


    Optional<AssetCategories> findAssetCategoryByName(String name);
    List<AssetCategories> findAllAssetCategoriesByIdIn(List<Integer> idCategory);
}
