package com.example.csvccdshustbe.response.process;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessAssetResponse {


    private String codeDocument;

    private Integer idUserCreate;
    private String codeUserCreate;
    private String nameUserCreate;
    private String codeDepartment;
    private String nameDepartment;
    private Integer status;
    private String timeCreated;
    private String timeModified;
    private String timeDocument;
    private String timeIncrease;
}
