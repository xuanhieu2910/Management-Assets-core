package com.example.csvccdshustbe.request.assetCategories;

import com.example.csvccdshustbe.request.RequestPageBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesRequest extends RequestPageBase {

    @NotNull
    @JsonProperty(namespace = "codeName", required = true)
    private String codeName;
}
