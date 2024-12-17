package com.example.csvccdshustbe.dto.document;

import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsDocumentDto {

    private Integer idDocument;
    private String codeDocument;
    private String fullName;
    private String userName;
    private String description;
    private Integer status;
    private Long timeCreated;
    private Long timeModified;
    private String timeIncrease;
    private String timeDocument;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private List<BluePrintStateDto> bluePrintStateDto;
    private Integer statusDocument;
    private Integer idProcess;

}
