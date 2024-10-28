package com.example.csvccdshustbe.response.report;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindAllReportResponse {

    @JsonProperty("id_report")
    private Integer idReport;
    @JsonProperty("code_report")
    private String codeReport;
    @JsonProperty("title_report")
    private String titleReport;
    @JsonProperty("id_government_circular")
    private Integer idGovernmentCircular;
    @JsonProperty("title_government_circular")
    private String titleGovernmentCircular;

}
