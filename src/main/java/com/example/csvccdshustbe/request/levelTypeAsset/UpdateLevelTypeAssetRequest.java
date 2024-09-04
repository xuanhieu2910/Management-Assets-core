package com.example.csvccdshustbe.request.levelTypeAsset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLevelTypeAssetRequest {
    @NonNull
    private Integer idLevelTypeAsset;
    @NonNull
    private String name;
    private Integer level;
    private String description;
    @NonNull
    private Integer status;
}
