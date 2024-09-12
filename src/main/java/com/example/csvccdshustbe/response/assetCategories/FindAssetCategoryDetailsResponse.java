package com.example.csvccdshustbe.response.assetCategories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAssetCategoryDetailsResponse {
    private String codeAssetCategory;
    private Integer idParentAssetCategory;
    private String nameAssetCategory;
    private Integer idInstance;
}
