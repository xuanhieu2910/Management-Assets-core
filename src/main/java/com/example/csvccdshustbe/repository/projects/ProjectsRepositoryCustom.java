package com.example.csvccdshustbe.repository.projects;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepositoryCustom {

    Page<FindAllProjectsDto> findAllProjectVisible(Pageable pageable, FindAllProjectsRequest request);
    Page<FindAllProjectsDto> findAllProject(Pageable pageable, FindAllProjectsRequest request);
    Optional<Projects> findProjectByName(String name);

    Optional<Projects> findProjectByIdParent(Integer idParent);

    Optional<Projects> findProjectById(Integer idProject);

    Optional<Projects> findProjectsByIdAndStatus(Integer idProject, Integer status);

    boolean checkExitsProjectByNameOrCodeOrShortName(String name, String shortName);

    boolean isExitsAssetByIdProject(Integer idProject);
    List<FindAllProjectsDto> findAllProjectsToDownload();

    List<Projects> findAllProjectById(List<Integer> idProject);
}
