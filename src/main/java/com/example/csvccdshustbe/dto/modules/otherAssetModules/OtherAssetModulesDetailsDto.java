package com.example.csvccdshustbe.dto.modules.otherAssetModules;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtherAssetModulesDetailsDto {

    private Integer idOtherAssetModule;
    private Integer idAsset;
    private String label;
    private String model;
    private String serial;
    private String publishDate;
    private Integer idCountryProducer;
    private Integer idUser;
    private Integer idTypeUse;
    private String nameCountryProducer;
    private String userName;
    private String fullName;
    private String nameTypeUse;
}
