package com.example.csvccdshustbe.request.document.tool;

import com.example.csvccdshustbe.request.toolProcess.UpdateAllToolProcessRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryToolRequest {

    private String codeDocument;
    private UpdateAllToolProcessRequest toolProcess;
    private boolean isUpdateFinished;

}
