package com.example.csvccdshustbe.dto.originalOfFormationTool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllOriginalOfFormationToolDto {

    private Integer idOriginalOfFormationTool;
    private String name;
    private String shortName;
    private String codeName;
    private String description;
    private Integer parent;
    private String sortOrder;
    private Integer visible;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private String nameParent;
}
