package com.example.csvccdshustbe.request.process.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentInventoryAssetRequest {

    private String codeDocument;
    private String timeCreatedDocument;
    private String timeInventory;
    private String description;
    private Integer idDepartment;
}
