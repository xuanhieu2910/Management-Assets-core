package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.asset.AssetDetailDecreaseRequest;
import com.example.csvccdshustbe.request.process.councilInventory.CreateCouncilDecreaseRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentDecreaseAssetRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateDecreaseAssetRequest {

    private CreateDocumentDecreaseAssetRequest document;
    private List<AssetDetailDecreaseRequest> assetDetail;
    private String typeProcess;
    private List<CreateCouncilDecreaseRequest> councilDecrease;
}
