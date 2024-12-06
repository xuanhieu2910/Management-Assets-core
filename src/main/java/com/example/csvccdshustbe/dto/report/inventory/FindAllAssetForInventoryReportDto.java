package com.example.csvccdshustbe.dto.report.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllAssetForInventoryReportDto {

    private Integer idAsset;
    private Integer idProcess;
    private Integer idAssetProcess;
    private String value;
    private String codeDocument;
}
