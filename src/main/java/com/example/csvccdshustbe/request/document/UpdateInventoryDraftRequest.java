package com.example.csvccdshustbe.request.document;

import com.example.csvccdshustbe.request.assetProcess.UpdateAllAssetProcessRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateInventoryDraftRequest {

    private String codeDocument;
    private UpdateAllAssetProcessRequest assetProcess;
}
