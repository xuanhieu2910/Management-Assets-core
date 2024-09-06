package com.example.csvccdshustbe.dto.original;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalDto {


    private Integer idOriginal;
    private String name;
    private String shortName;
    private String description;
    private Integer parent;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private String hardCodeDev;
    private Integer isDefault;
    private Integer visible;
    private String sortOrder;
    private Integer idUserCreated;
    private Integer idUserModified;
    private Integer idAssetCategory;
}
