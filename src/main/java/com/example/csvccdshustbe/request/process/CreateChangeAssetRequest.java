package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.asset.AssetDetailChangeRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentChangeAssetRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateChangeAssetRequest {

    private CreateDocumentChangeAssetRequest document;
    private AssetDetailChangeRequest assetDetail;
    private String typeProcess;

}
