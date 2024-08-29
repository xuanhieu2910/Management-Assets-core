package com.example.csvccdshustbe.request.assetCategories;

import com.example.csvccdshustbe.request.RequestPageBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesRequest extends RequestPageBase {

    @JsonProperty(value = "code_name", required = true)
    private String codeName;
}
