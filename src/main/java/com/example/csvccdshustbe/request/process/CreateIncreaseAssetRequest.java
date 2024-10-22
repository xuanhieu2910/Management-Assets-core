package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateIncreaseAssetRequest {

    private CreateDocumentRequest document;
    private List<Integer> idsAsset;
    private String typeProcess;
}
