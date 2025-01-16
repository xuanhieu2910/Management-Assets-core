package com.example.csvccdshustbe.response.toolProcess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllToolProcessResponse {
    @JsonProperty("code_tool")
    private String codeTool;
    @JsonProperty("name_tool")
    private String nameTool;
    @JsonProperty("name_tool_category")
    private String nameToolCategory;
    @JsonProperty("code_tool_category")
    private String codeToolCategory;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("id_tool")
    private Integer idTool;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("value")
    private String value;
    @JsonProperty("id_tool_process")
    private Integer idToolProcess;
    @JsonProperty("status")
    private Integer status;

}
