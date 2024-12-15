package com.example.csvccdshustbe.request.assetProcess;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAllAssetProcessRequest {

    private List<AssetProcessRequest> assets;
    private List<AssetProcessRequest> fluctuatingSituationAsset;
    private Integer idProcess;
}
