package com.example.csvccdshustbe.request.goalsUseGround;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateGoalsUseGroundRequest {
    @NonNull
    private Integer idGoalsUseGround;
    @NonNull
    private String name;
    private String code;
    @NonNull
    private Integer status;
}
