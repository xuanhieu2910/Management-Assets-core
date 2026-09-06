package com.example.csvccdshustbe.dto.suppliers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllSuppliersDto {

    private Integer idSupplier;
    private String name;
    private String phoneNumber;
    private String email;
    private String fax;
    private String address;
    private String url;
    private String notes;
    private Integer idDepartment;
    private String nameDepartment;
}
