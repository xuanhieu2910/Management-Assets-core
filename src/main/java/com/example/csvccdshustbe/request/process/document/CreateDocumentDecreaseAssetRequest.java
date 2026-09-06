package com.example.csvccdshustbe.request.process.document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentDecreaseAssetRequest {

    private String codeDocument;
    private String timeDecrease;
    private String timeDocument;
    private String description;

}
