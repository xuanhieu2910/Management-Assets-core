package com.example.csvccdshustbe.request.process.councilInventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCouncilDecreaseRequest {

    private String userName;
    private String codeUser;
    private String position;
    private String positionInstance;
    private Integer level;
    private Integer idDepartment;
}
