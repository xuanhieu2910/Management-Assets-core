package com.example.csvccdshustbe.dto.requestStakeHolder;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStakeHolderDetails {

    private Integer idRequestStakeHolder;
    private Integer idRequest;
    private Integer idUser;
    private Integer idDepartment;
    private String userName;
    private String fullName;
    private Integer status;
    private String timeCreated;
    private String timeModified;
    private String reason;
    private String description;
    private Integer idReason;
}
