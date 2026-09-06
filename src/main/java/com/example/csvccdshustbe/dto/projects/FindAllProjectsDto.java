package com.example.csvccdshustbe.dto.projects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllProjectsDto {


    private Integer idProject;
    private String name;
    private String shortName;
    private Integer parent;
    private Integer depth;
    private String path;
    private String timeCreated;
    private String timeModified;
    private Integer visible;
    private String nameParent;
    private Integer idDepartment;
    private String nameDepartment;

}
