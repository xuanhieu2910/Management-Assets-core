package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.asset.InformationAssetInventoryRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateIncreaseAssetRequest {

    private CreateDocumentRequest document;
    private List<InformationAssetInventoryRequest> assetProcessValue;
    private String typeProcess;
}
