package com.example.csvccdshustbe.response.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentSResponse {

    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
}
