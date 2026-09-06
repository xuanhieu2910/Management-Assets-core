package com.example.csvccdshustbe.response.tool;
import com.example.csvccdshustbe.dto.tool.AllocateToolDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsToolResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("code_tool")
    private String codeTool;
    @JsonProperty("code_tool_category")
    private String codeToolCategory;
    @JsonProperty("id_tool_category")
    private Integer idToolCategory;
    @JsonProperty("name_tool_category")
    private String nameToolCategory;
    @JsonProperty("id_parent")
    private Integer idParent;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("value")
    private String value;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("name_location")
    private String nameLocation;
    @JsonProperty("is_increase")
    private Integer isIncrease;
    @JsonProperty("is_decrease")
    private Integer isDecrease;
    @JsonProperty("status_use")
    private Integer statusUse;
    @JsonProperty("year_use")
    private String yearUse;
    @JsonProperty("id_user_use")
    private Integer idUserUse;
    @JsonProperty("name_user_use")
    private String nameUserUse;
    @JsonProperty("allocate_tool_list")
    private List<AllocateToolDto> allowcateToolDtoList;
}
