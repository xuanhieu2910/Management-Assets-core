package com.example.csvccdshustbe.request.suppliers;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class FindAllSuppliersRequest extends RequestPageBase {
    private Integer status;
}
