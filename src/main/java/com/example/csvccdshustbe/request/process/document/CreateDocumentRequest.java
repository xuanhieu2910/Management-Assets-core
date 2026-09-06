package com.example.csvccdshustbe.request.process.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentRequest {

    private String codeDocument;
    private String timeIncrease;
    private String timeDocument;
    private String description;
}
