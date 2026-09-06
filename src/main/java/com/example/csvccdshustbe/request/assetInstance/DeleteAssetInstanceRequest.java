package com.example.csvccdshustbe.request.assetInstance;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteAssetInstanceRequest {

    List<AssetInstanceRequest> assetInstances;

}
