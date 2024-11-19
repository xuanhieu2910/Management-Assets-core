package com.example.csvccdshustbe.request.assetInstance;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetInstanceRequest extends RequestPageBase {
    private Integer isError;
}
