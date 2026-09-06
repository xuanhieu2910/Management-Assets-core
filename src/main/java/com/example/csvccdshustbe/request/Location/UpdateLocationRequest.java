package com.example.csvccdshustbe.request.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLocationRequest {
    @NonNull
    private  Integer idLocation;
    private Integer parentId;
    @NonNull
    private String name;
    private String shortName;
    private Integer visible;
    private Integer idDepartment;
    private String description;
}
