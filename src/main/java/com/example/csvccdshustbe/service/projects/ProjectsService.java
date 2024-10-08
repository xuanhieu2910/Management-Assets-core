package com.example.csvccdshustbe.service.projects;
import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.projects.CreateProjectsRequest;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateStatusProjectRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectsService {

    Page<FindAllProjectsResponse> findAllProjectsVisibleResponse(FindAllProjectsRequest request);
    Page<FindAllProjectsResponse> findAllProjectsResponse(FindAllProjectsRequest request);
    void createProject(CreateProjectsRequest request) throws ValidateFiledException;

    void updateProject(UpdateProjectsRequest request) throws ValidateFiledException;

    void deleteProjectByIdProject(Integer idProject) throws ValidateFiledException;

    Projects findProjectsByIdProjectAndStatus(Integer idProject, Integer status);

    void updateStatusProject(UpdateStatusProjectRequest request) throws ValidateFiledException;

    List<FindAllProjectsDto> findAllProjectToDownload();
}
