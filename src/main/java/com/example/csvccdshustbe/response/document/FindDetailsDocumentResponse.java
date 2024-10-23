package com.example.csvccdshustbe.response.document;


import com.example.csvccdshustbe.dto.document.BluePrintDocumentDto;
import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import com.example.csvccdshustbe.dto.typeProcess.BluePrintTypeProcessAssetDto;
import com.example.csvccdshustbe.dto.typeProcess.BluePrintTypeProcessDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsDocumentResponse {
    @JsonProperty("document")
    private BluePrintDocumentDto document;
    @JsonProperty("type_process")
    private BluePrintTypeProcessDto typeProcess;
    @JsonProperty("user_create")
    private String userCreate;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("asset")
    private List<BluePrintTypeProcessAssetDto>assetDto;
    @JsonProperty("state")
    private List<BluePrintStateDto>stateDtos;

}
