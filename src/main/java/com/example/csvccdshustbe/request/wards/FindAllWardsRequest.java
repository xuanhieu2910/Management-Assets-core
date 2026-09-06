package com.example.csvccdshustbe.request.wards;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllWardsRequest extends RequestPageBase {

    @NotNull
    private String codeDistrict;
}
