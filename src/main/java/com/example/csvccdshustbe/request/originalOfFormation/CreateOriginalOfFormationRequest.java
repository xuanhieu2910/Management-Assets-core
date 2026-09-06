package com.example.csvccdshustbe.request.originalOfFormation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateOriginalOfFormationRequest {

    @NonNull
    private String name;
    private String shortName;
    private String codeName;
    private String description;
    private Integer parentId;
    private String sortOrder;
    private Integer visible;
}
