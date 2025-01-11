package com.example.csvccdshustbe.dto.toolCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolCategoryDto {
    private Integer idToolCategory;
    private String name;
    private String codeTool;
    private String shortName;
    private String description;
    private Integer parent;
    private String sortOrder;
    private Integer toolCount;
    private Integer visible;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private String nameParent;
    private Integer idDepartmentOriginal;
    private Integer isLeaf;
}
