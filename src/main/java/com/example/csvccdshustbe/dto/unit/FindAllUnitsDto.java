package com.example.csvccdshustbe.dto.unit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllUnitsDto {

    private Integer idUnit;
    private String nameUnit;
    private Integer status;
    private String timeCreated;
    private String timeModified;
}
