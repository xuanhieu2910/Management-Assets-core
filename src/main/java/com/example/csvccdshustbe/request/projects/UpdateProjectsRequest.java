package com.example.csvccdshustbe.request.projects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProjectsRequest {
    @NonNull
    private Integer idProject;
    private Integer parentId;
    @NonNull
    private String name;
    private String shortName;
    private Integer visible;
}
