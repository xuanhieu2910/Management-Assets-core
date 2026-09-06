package com.example.csvccdshustbe.dto.originalTool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalToolDto {
    private Integer idOriginalTool;
    private String name;
    private String shortName;
    private String description;
    private Integer parent;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private Integer visible;
    private String sortOrder;
    private Integer idUserCreated;
    private Integer idUserModified;
    private Integer idToolCategory;
    private String code;
    private Integer isLeaf;
}
