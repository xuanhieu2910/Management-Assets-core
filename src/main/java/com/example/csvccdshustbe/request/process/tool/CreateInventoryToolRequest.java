package com.example.csvccdshustbe.request.process.tool;

import com.example.csvccdshustbe.request.process.document.CreateDocumentInventoryToolRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateInventoryToolRequest {
    private CreateDocumentInventoryToolRequest document;
    private List<ToolDetailInventoryRequest> toolDetail;
    private String typeProcess;
}
