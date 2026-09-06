package com.example.csvccdshustbe.request.fluctuatingSituationAsset;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FluctuatingSituationAssetRequest {

    private List<Integer> idsFluctuatingSituationAsset;
    private Integer typeCurrent;
}
