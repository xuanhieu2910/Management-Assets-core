package com.example.csvccdshustbe.dto.projects;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintProjectsDto {

    @JsonProperty("id_project")
    private Integer idProjects;
    @JsonProperty("name")
    private String nameProjects;
}
