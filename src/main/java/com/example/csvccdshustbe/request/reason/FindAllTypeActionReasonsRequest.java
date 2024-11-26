package com.example.csvccdshustbe.request.reason;

import com.example.csvccdshustbe.request.RequestPageBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTypeActionReasonsRequest extends RequestPageBase {
    @NotNull
    private Integer status;
    private Integer typeReason;
    @NotNull
    private String typeAction;
}
