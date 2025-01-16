package com.example.csvccdshustbe.response.originalTool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor

public class FindAllOriginalToolVisibleResponse {
    @JsonProperty("id_original_tool")
    private Integer idOriginalTool;
    @JsonProperty("name")
    private String name;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("visible")
    private Integer visible;
    @JsonProperty("id_tool_category")
    private Integer idToolCategory;
}
