package com.example.csvccdshustbe.request.projects;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusProjectRequest {

    private Integer idProject;
    private Integer status;
}
