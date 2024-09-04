package com.example.csvccdshustbe.request.units;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUnitsRequest {
    private Integer idAssetCategory;
    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
