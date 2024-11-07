package com.example.csvccdshustbe.request.process.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetDetailInventoryRequest {

    private Integer idAsset;
    private Integer quantity;
    private String originalValue;
    private String restValue;

}
