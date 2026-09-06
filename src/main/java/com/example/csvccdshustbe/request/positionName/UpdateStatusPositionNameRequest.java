package com.example.csvccdshustbe.request.positionName;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusPositionNameRequest {

    private Integer idPositionName;
    private Integer status;
}
