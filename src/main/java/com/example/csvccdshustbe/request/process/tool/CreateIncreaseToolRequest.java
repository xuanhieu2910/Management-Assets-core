package com.example.csvccdshustbe.request.process.tool;

import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateIncreaseToolRequest {

    private CreateDocumentRequest document;
    private List<ToolDetailIncreaseRequest> toolsDetail;
    private String typeProcess;
}
