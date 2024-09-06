package com.example.csvccdshustbe.repository.assetCategories;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssetCategoriesRepositoryCustom {

    List<AssetCategories> findAllAssetCategoriesIsPickedAndVisible();

    Page<FindAllAssetCategoriesByCodeAndVisibleDto>
    findAllAssetCategoriesByCodeAndVisible(Pageable pageable, FindAllAssetCategoriesRequest request);
    Optional<AssetCategories> findAssetCategoriesVisibleByCodeName(String codeName);

    boolean checkAssetCategoriesByParentIdAndName(Integer parentId, String name);

    Optional<AssetCategories> findAssetCategoryParentByParentId(Integer parentId);
    Optional<AssetCategories> findAssetCategoryById(Integer idAssetCategory);
    boolean checkExitsAssetCategoriesByNameOrShortName(String name, String shortName);
}
