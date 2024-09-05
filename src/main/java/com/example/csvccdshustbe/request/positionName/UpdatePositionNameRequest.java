package com.example.csvccdshustbe.request.positionName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePositionNameRequest {
    @NonNull
    private Integer idPositionName;
    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
