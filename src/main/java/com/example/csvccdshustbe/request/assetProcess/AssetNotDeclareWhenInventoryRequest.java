package com.example.csvccdshustbe.request.assetProcess;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssetNotDeclareWhenInventoryRequest {

    @NotNull
    private Integer idProcess;
    private List<AssetProcessNotDeclareInventoryRequest> listAssetDeclare;
}
