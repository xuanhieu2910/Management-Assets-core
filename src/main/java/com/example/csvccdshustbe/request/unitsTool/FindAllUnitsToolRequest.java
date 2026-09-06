package com.example.csvccdshustbe.request.unitsTool;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUnitsToolRequest extends RequestPageBase {
    private Integer status;
}
