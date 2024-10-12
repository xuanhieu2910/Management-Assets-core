package com.example.csvccdshustbe.request.assetCategories;

import com.example.csvccdshustbe.request.RequestPageBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetCategoriesVisibleRequest extends RequestPageBase {

    @NotNull
    @JsonProperty(namespace = "codeName")
    private String codeName;
    private List<Integer> idsDepartment;
}
