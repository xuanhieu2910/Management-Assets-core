package com.example.csvccdshustbe.dto.tool;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolImportDto {

    private Integer idToolCategory;
    private String nameToolCategory;
    private String nameTool;
    private Integer idSupply;
    private String nameSupply;
    private Integer idDocumentAttach;
    private String nameDocumentAttach;
    private Integer idProject;
    private String nameProject;
    private String yearUsed;
    private Integer idOriginal;
    private String nameOriginal;
    private Integer idOriginalOfFormation;
    private String nameOriginalOfFormation;
    private Integer idUnit;
    private String nameUnit;
    private String price;
    private Integer idDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private String userName;
    private Integer statusUse;
    private Integer quantity;
    private Integer typeAllocate;
    private Integer amountAllocate;
    private Integer amountAllocated;
    private Integer idMedicineType;
    private String nameMedicineType;
    private Integer idMedicineGroup;
    private String nameMedicineGroup;
    private String timeProduced;
    private String timeExpiry;
    private Integer numberUsed;
    private Integer numberLot;
    private String nameOwner;
    private String addressOwner;
    private List<String> errors;

}
