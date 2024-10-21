package com.example.csvccdshustbe.request.process.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentIncreaseRequest {

    private String codeDocument;
    private String timeIncrease;
    private String description;
}
