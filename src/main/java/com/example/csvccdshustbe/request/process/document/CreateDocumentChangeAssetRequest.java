package com.example.csvccdshustbe.request.process.document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentChangeAssetRequest {

    private String codeDocument;
    private String timeChange;
    private String timeDocument;
    private String description;
}
