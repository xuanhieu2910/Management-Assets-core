package com.example.csvccdshustbe.request.assetProcess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetProcessRequest {

    private Integer idAsset;
    private String value;
    private Integer typeFluctuatingSituationAsset;
}
