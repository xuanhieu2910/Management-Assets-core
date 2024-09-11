package com.example.csvccdshustbe.dto.modules.architectureModules;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchitectureModulesDetailsDto {


    private Integer idArchitectureModule;
    private Integer idAsset;
    private Integer idInstance;
    private Double length;
    private Double acreage;
    private Double volume;
    private String publishDate;
    private Integer idCountryProducer;
    private String nameInstance;
    private String nameCountryProducer;
}
