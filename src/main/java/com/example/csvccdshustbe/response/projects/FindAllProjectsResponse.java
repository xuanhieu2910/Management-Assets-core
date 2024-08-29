package com.example.csvccdshustbe.response.projects;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllProjectsResponse {

    @JsonProperty("id_project")
    private Integer idProject;
    @JsonProperty("name")
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
}
