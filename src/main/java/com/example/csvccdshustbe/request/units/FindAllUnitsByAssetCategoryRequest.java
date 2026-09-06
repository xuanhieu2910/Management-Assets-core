package com.example.csvccdshustbe.request.units;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUnitsByAssetCategoryRequest extends RequestPageBase {

    @NotNull
    private String codeName;
}
