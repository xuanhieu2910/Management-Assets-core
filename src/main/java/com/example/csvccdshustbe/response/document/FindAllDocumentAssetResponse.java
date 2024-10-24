package com.example.csvccdshustbe.response.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllDocumentAssetResponse {


    private String codeTypeProcess;
    private String nameTypeProcess;
    private String codeDocument;
    private String nameUserCreate;
    private String codeDepartment;
    private String nameDepartment;
    private Integer status;
    private String timeCreated;
    private String description;

}
