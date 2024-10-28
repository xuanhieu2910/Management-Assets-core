package com.example.csvccdshustbe.dto.positionName;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllPositionNameDto {


    private Integer idPositionName;
    private String name;
    private Integer status;
    private Long timeCreated;
    private Long timeModified;

}
