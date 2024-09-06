package com.example.csvccdshustbe.request.positionName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePositionNameRequest {

    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
