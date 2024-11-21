package com.example.csvccdshustbe.request.process.document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentRevaluationAssetRequest {

    private String codeDocument;
    private String timeRevaluation;
    private String timeDocument;
    private String description;
}
