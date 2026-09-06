package com.example.csvccdshustbe.request.documentAttack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusDocumentAttackRequest {

    private Integer idDocumentAttack;
    private Integer status;
}
