package com.example.csvccdshustbe.request.assetCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAssetCategoryRequest {

    @NonNull
    private String name;
    private String shortName;
    private String description;
    private Integer parentId;
    private Integer visible;
    private String pathImage;
    private Integer isPick;
    private String valueWearTear;
    private String yearUsedWearTear;
    private String minimumTimeDepreciation;
    private String maximumTimeDepreciation;
}
