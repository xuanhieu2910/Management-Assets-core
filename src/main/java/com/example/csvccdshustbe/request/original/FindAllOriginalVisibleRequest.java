package com.example.csvccdshustbe.request.original;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalVisibleRequest extends RequestPageBase {

    @NotNull
    private Integer idParentAssetCategory;
}
