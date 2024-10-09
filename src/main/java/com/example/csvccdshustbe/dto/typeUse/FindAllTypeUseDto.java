package com.example.csvccdshustbe.dto.typeUse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllTypeUseDto {

    private Integer idTypeUse;
    private String nameTypeUse;
    private Integer status;
    private String timeCreated;
    private String timeModified;
}
