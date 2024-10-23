package com.example.csvccdshustbe.dto.state;

import com.example.csvccdshustbe.dto.requestStakeHolder.BluePrintRequestStakeHolderDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BluePrintStateDto {

    @JsonProperty("id_state")
    private Integer idState;
    @JsonProperty("step")
    private int step;
    @JsonProperty("code_type_state")
    private String codeTypeState;
    @JsonProperty("status")
    private int status;
    @JsonProperty("request_stake_holder")
    private List<BluePrintRequestStakeHolderDto> requestStakeHolder;

}
