package com.example.csvccdshustbe.response.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @JsonProperty("path_report")
    private String pathReport;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("status")
    private Integer status;

}
