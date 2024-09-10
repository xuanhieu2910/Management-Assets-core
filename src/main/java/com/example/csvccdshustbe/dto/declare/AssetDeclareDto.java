package com.example.csvccdshustbe.dto.declare;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetDeclareDto {

    private BluePrintDeclareDto bluePrintDeclare;
    private Object dataDetail;
}
