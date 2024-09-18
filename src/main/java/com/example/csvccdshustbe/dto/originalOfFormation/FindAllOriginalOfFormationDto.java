package com.example.csvccdshustbe.dto.originalOfFormation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalOfFormationDto {

    private Integer idOriginalOfFormation;
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
