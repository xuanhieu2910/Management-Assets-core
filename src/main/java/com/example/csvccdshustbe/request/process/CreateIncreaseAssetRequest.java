package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.process.document.CreateDocumentIncreaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateIncreaseAssetRequest {

    private CreateDocumentIncreaseRequest document;
    private List<Integer> idsAsset;
}
