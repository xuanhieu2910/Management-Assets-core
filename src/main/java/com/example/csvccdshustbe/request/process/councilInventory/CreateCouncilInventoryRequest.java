package com.example.csvccdshustbe.request.process.councilInventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCouncilInventoryRequest {

    private String userName;
    private String codeUser;
    private String position;
    private String positionInstance;
    private Integer level;
    private Integer idDepartment;
}
