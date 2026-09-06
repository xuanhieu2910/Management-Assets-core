package com.example.csvccdshustbe.request.typeDeclareAsset;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTypeDeclareAssetActiveRequest extends RequestPageBase {

    @NotNull
    private String idAssetCategory;

}
