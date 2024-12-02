package com.example.csvccdshustbe.dto.assetProcess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetProcessDto {

    private Integer idAssetProcess;
    private Integer idAsset;
    private Integer idProcess;
    private Integer idTypeProcess;
    private Integer status;
    private String value;
    private String timeCreated;
    private String timeModified;
}
