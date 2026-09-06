package com.example.csvccdshustbe.request.methodBuyAsset;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMethodBuyAssetRequest {
    @NotNull
    private String title;
    private Integer status;
}
