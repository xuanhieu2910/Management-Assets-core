package com.example.csvccdshustbe.response.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentResponse {
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("path")
    private String path;
}
