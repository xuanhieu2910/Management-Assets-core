package com.example.csvccdshustbe.request.documentAttack;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAttackRequest extends RequestPageBase {

    private Integer status;
}
