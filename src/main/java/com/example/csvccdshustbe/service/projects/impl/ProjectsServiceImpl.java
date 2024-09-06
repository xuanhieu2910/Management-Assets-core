package com.example.csvccdshustbe.service.projects.impl;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.Projects;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.projects.ProjectsRepository;
import com.example.csvccdshustbe.request.projects.CreateProjectsRequest;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectsServiceImpl implements ProjectsService {


    @Autowired
    ProjectsRepository projectsRepository;

    @Override
    public Page<FindAllProjectsResponse> findAllProjectsResponseByName(FindAllProjectsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllProjectsDto> dtos = projectsRepository.findAllProjectVisible(pageable, request);
        return new PageImpl<>(convertToFindAllProjectsResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    private List<FindAllProjectsResponse> convertToFindAllProjectsResponse(List<FindAllProjectsDto> collect) {
        List<FindAllProjectsResponse> responses = new ArrayList<>();
        for (FindAllProjectsDto allProjectsDto: collect){
            FindAllProjectsResponse res = new FindAllProjectsResponse();
            res.setIdProject(allProjectsDto.getIdProject());
            res.setName(allProjectsDto.getName());
            res.setShortName(allProjectsDto.getShortName());
            res.setParent(allProjectsDto.getParent());
            res.setDepth(allProjectsDto.getDepth());
            res.setPath(allProjectsDto.getPath());
            responses.add(res);
        }
        return responses;
    }

    @Override
    public void createProject(CreateProjectsRequest request) throws ValidateFiledException {
        validateDataCreateProjects(request);
        projectsRepository.save(contructProjects(request));
    }



    @Override
    public void updateProject(UpdateProjectsRequest request) throws ValidateFiledException {
        Projects projects = validateDataUpdateProjects(request);
        projectsRepository.save(editProject(projects, request));
    }


    @Override
    public void deleteProjectByIdProject(Integer idProject) {
        Optional<Projects> projectsOptional = projectsRepository.findProjectById(idProject);
        if (!projectsOptional.isPresent()){
            throw new NotFoundException("Don't exits Project by id!");
        }
        projectsRepository.delete(projectsOptional.get());
    }

    @Override
    public Projects findProjectsByIdProjectAndStatus(Integer idProject, Integer status) {
        Optional<Projects> projects = projectsRepository.findProjectsByIdAndStatus(idProject, status);
        if (!projects.isPresent()) {
            throw new NotFoundException("Don't exits projects by id!");
        }
        return projects.get();
    }

    private void validateDataCreateProjects(CreateProjectsRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<Projects> projectsOptional = projectsRepository.findProjectByName(request.getName());
        if (projectsOptional.isPresent()){
            throw new ValidateFiledException("Exits Project by name of Project!");
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            if (request.getShortName().equals(projectsOptional.get().getShortName())){
                throw new ValidateFiledException("Exits Project by short name");
            }
        }

        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Projects> projects = projectsRepository.findProjectByIdParent(request.getParentId());
            if (!projects.isPresent()){
                throw new ValidateFiledException("Don't exits Project by id parent!");
            }
        }
    }
    private Projects contructProjects(CreateProjectsRequest request) {
        Projects projects = new Projects();
        projects.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getShortName())){
            projects.setShortName(request.getShortName());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())){
            projects.setParent(request.getParentId());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            projects.setVisible(request.getVisible());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        projects.setTimeCreated(timeCurrent);
        projects.setTimeModified(timeCurrent);
        return projects;
    }

    private Projects validateDataUpdateProjects(UpdateProjectsRequest request) throws ValidateFiledException{
        Optional<Projects> projectsOptional = projectsRepository.findProjectById(request.getIdProject());
        if (!projectsOptional.isPresent()) {
            throw new NotFoundException("Don't exits project by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!projectsOptional.get().getName().equals(request.getName()) ||
                !projectsOptional.get().getShortName().equals(request.getShortName())) {
            if (projectsRepository.checkExitsProjectByNameOrCodeOrShortName(request.getName(),
                    request.getShortName())) {
                throw new ValidateFiledException("Exits project by name or short name!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Projects> projectByIdParent = projectsRepository.findProjectByIdParent(request.getParentId());
            if (!projectByIdParent.isPresent()){
                throw new ValidateFiledException("Don't exits project by id parent!");
            }
        }

        return projectsOptional.get();
    }

    private Projects editProject(Projects projects, UpdateProjectsRequest request) {
        projects.setName(request.getName());
        projects.setShortName(request.getShortName());
        projects.setParent(request.getParentId());
        projects.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        projects.setTimeModified(timeModified);
        return projects;
    }

}
