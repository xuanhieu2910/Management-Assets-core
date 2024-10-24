package com.example.csvccdshustbe.dto.request;


import com.example.csvccdshustbe.dto.requestData.RequestDataDetailsDto;
import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDetailsDto {

    private Integer idRequest;
    private Integer idProcess;
    private Integer idState;
    private String name;
    private String description;
    private Integer status;
    private String timeCreated;
    private String timeModified;
    private List<RequestDataDetailsDto> requestData;
    private List<RequestStakeHolderDetails> requestStakeHolder;
}
