package com.example.csvccdshustbe.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllReportDto {
    private Integer idReport;
    private String codeReport;
    private String titleReport;
    private String path;
    private Long timeCreated;
    private Long timeModified;
    private Integer idUserCreated;
    private Integer idUserModified;
    private Integer status;
    private String typeMime;
    private Integer idGovernmentCircular;
    private String titleGovernmentCircular;
    private String pathImage;
}
