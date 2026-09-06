package com.example.csvccdshustbe.request.assetInstance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAssetInstanceRequest {

    List<AssetInstanceRequest> assetInstances;

}
