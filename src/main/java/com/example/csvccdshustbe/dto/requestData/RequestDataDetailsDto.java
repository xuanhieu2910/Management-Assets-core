package com.example.csvccdshustbe.dto.requestData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDataDetailsDto {

    private Integer idRequestData;
    private Integer idRequest;
    private String name;
    private String value;
    private Integer status;
    private String timeCreated;
    private String timeModified;
}
