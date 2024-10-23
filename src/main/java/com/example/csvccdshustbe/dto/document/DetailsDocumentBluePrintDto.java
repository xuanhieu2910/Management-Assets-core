package com.example.csvccdshustbe.dto.document;

import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import com.example.csvccdshustbe.dto.typeProcess.BluePrintTypeProcessAssetDto;
import com.example.csvccdshustbe.dto.typeProcess.BluePrintTypeProcessDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DetailsDocumentBluePrintDto {

    private BluePrintDocumentDto document;
    private BluePrintTypeProcessDto typeProcess;
    private String userCreate;
    private Integer status;
    private List<BluePrintTypeProcessAssetDto> assetDto;
    private List<BluePrintStateDto>stateDtos;
}
