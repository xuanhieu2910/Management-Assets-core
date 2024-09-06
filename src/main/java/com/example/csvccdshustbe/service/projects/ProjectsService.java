package com.example.csvccdshustbe.service.projects;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.projects.CreateProjectsRequest;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import org.springframework.data.domain.Page;

public interface ProjectsService {

    Page<FindAllProjectsResponse> findAllProjectsResponseByName(FindAllProjectsRequest request);
    void createProject(CreateProjectsRequest request) throws ValidateFiledException;

    void updateProject(UpdateProjectsRequest request) throws ValidateFiledException;

    void deleteProjectByIdProject(Integer idProject);

    Projects findProjectsByIdProjectAndStatus(Integer idProject, Integer status);
}
