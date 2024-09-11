package com.example.csvccdshustbe.request.methodBuyAsset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMethodBuyAssetRequest {
    @NonNull
    private Integer idMethodBuyAsset;
    @NonNull
    private String title;
    private Integer status;
}
