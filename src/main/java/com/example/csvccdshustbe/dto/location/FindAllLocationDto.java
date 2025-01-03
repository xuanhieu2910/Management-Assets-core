package com.example.csvccdshustbe.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllLocationDto {
    private Integer idLocation;
    private Integer idDepartment;
    private String name;
    private String shortName;
    private Integer parent;
    private Integer visible;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private String nameParent;
    private String nameDepartment;
    private String description;
}
