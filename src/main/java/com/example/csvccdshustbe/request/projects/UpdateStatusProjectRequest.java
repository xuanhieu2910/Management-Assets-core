package com.example.csvccdshustbe.request.projects;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusProjectRequest {
    @NotNull
    private Integer idProject;
    @NotNull
    private Integer visible;
}
