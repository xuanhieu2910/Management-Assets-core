package com.example.csvccdshustbe.dto.assetCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesByCodeAndVisibleDto {

    private Integer idAssetCategory;
    private String name;
    private String codeName;
    private String shortName;
    private String description;
    private Integer parent;
    private String sortOrder;
    private Integer assetCount;
    private Integer visible;
    private String timeCreated;
    private String timeModified;
    private Integer isPick;
    private Integer depth;
    private String path;
    private String valueWearTear;
    private String yearUsedWearTear;
    private String minimumTimeDepreciation;
    private String maximumTimeDepreciation;
}
