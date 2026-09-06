package com.example.csvccdshustbe.request.Location;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllLocationVisibleRequest extends RequestPageBase {

    @NotNull
    private Integer idDepartment;
}
