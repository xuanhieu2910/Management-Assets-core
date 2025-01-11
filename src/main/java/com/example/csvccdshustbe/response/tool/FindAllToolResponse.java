package com.example.csvccdshustbe.response.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolResponse {

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
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("is_increase")
    private Integer isIncrease;
    @JsonProperty("is_decrease")
    private Integer isDecrease;
    @JsonProperty("value")
    private String value;
    @JsonProperty("year_use")
    private String yearUse;
    @JsonProperty("status_use")
    private Integer statusUse;

}
