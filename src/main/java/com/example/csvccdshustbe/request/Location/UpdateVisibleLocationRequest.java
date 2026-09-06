package com.example.csvccdshustbe.request.Location;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateVisibleLocationRequest {

    @NotNull
    private Integer idLocation;
    @NotNull
    private Integer visible;
}
