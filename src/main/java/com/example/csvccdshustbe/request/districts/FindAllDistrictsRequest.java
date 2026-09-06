package com.example.csvccdshustbe.request.districts;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllDistrictsRequest extends RequestPageBase {
    @NotNull
    private String codeProvince;
}
