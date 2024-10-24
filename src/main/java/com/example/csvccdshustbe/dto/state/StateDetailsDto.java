package com.example.csvccdshustbe.dto.state;


import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StateDetailsDto {

    private Integer idState;
    private Integer statusState;
    private Integer idTypeState;
    private String codeTypeState;
    private String nameTypeState;
    private String timeCreated;
    private String timeModified;
    private Integer idProcess;
    private Integer step;
    private List<RequestDetailsDto> requestDetails;
}
