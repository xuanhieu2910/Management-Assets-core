package com.example.csvccdshustbe.dto.document;

import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindDetailsDocumentDto {

    private Integer idDocument;
    private String codeDocument;
    private String fullName;
    private String userName;
    private Integer status;
    private String description;
    private String timeCreated;
    private String timeModified;
    private String timeIncrease;
    private String timeDocument;
    private BluePrintStateDto bluePrintStateDto;

}
