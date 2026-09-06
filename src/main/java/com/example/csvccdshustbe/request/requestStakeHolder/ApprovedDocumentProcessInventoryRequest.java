package com.example.csvccdshustbe.request.requestStakeHolder;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApprovedDocumentProcessInventoryRequest {


    @NotNull
    private String codeDocument;
    @NotNull
    private Integer status;
    private Integer idReason;
    private String description;

}
