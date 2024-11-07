package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.asset.AssetDetailInventoryRequest;
import com.example.csvccdshustbe.request.process.councilInventory.CreateCouncilInventoryRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentInventoryAssetRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateInventoryAssetRequest {

    private CreateDocumentInventoryAssetRequest document;
    private List<AssetDetailInventoryRequest> assetDetail;
    private String typeProcess;
    private List<CreateCouncilInventoryRequest> councilInventory;
}
