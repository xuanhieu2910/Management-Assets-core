package com.example.csvccdshustbe.dto.modules.machineModules;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MachineModuleDetailsDto {

    private Integer idMachineModule;
    private Integer idAsset;
    private String labelMachine;
    private String model;
    private String serial;
    private String publishDate;
    private Integer idCountryProducer;
    private Integer idUser;
    private Integer idTypeUse;
    private String nameCountryProducer;
    private String nameUser;
    private String fullName;
    private String nameTypeUse;

}
