package com.example.csvccdshustbe.dto.report.inventory;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlueprintInventoryReportDto {

    private String codeDocument;
    private String timeInventory;
    private String timeDocument;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;

    private List<CouncilInventoryReportDto> councilInventoryReportDtos;
}
