package com.example.csvccdshustbe.request.documentAttack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateDocumentAttackRequest {
    private Integer idDepartment;
    @NonNull
    private String name;
    private String code;
    private String dateDeterminationDocument;
    @NonNull
    private Integer status;
}
