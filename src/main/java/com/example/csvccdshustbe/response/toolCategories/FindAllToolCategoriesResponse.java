package com.example.csvccdshustbe.response.toolCategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolCategoriesResponse {
    @JsonProperty("id_tool_category")
    private Integer idToolCategory;
    @JsonProperty("name")
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("code_tool")
    private String codeTool;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("name_parent")
    private String nameParent;
    @JsonProperty("visible")
    private Integer visible;
    @JsonProperty("is_default")
    private Integer isDefault;
}
