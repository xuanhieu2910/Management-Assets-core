package com.example.csvccdshustbe.request.process.tool;

import com.example.csvccdshustbe.request.process.document.CreateDocumentDecreaseAssetRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateDecreaseToolRequest {
    private CreateDocumentDecreaseAssetRequest document;
    private List<ToolDetailDecreaseRequest> toolsDetail;
    private String typeProcess;
}
