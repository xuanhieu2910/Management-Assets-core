package com.example.csvccdshustbe.request.documentAttack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDocumentAttackRequest {
    private Integer idDocumentAttack;
    private Integer idDepartment;
    @NonNull
    private String name;
    private String code;
    private String dateDeterminationDocument;
    @NonNull
    private Integer status;
}
