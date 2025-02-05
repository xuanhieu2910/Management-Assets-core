package com.example.csvccdshustbe.service.projects.impl;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.projects.ProjectsRepository;
import com.example.csvccdshustbe.request.projects.CreateProjectsRequest;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateStatusProjectRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectsServiceImpl implements ProjectsService {


    @Autowired
    ProjectsRepository projectsRepository;

    @Override
    public Page<FindAllProjectsResponse> findAllProjectsVisibleResponse(FindAllProjectsRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllProjectsDto> dtos = projectsRepository.findAllProjectVisible(pageable, request);
        return new PageImpl<>(convertToFindAllProjectsResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    @Override
    public Page<FindAllProjectsResponse> findAllProjectsResponse(FindAllProjectsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllProjectsDto> dtos = projectsRepository.findAllProject(pageable, request);
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
            res.setNameParent(allProjectsDto.getNameParent());
            res.setVisible(allProjectsDto.getVisible());
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
    public void deleteProjectByIdProject(Integer idProject) throws ValidateFiledException {
        Optional<Projects> projectsOptional = projectsRepository.findProjectById(idProject);
        if (projectsOptional.isEmpty()){
            throw new NotFoundException("Don't exits Project by id!");
        }
        if (projectsRepository.isExitsAssetByIdProject(idProject)){
            throw new ValidateFiledException("Exits asset by project id, can't delete project");
        }
        projectsRepository.delete(projectsOptional.get());
    }

    @Override
    public Projects findProjectsByIdProjectAndStatus(Integer idProject, Integer status) {
        Optional<Projects> projects = projectsRepository.findProjectsByIdAndStatus(idProject, status);
        if (projects.isEmpty()) {
            throw new NotFoundException("Don't exits projects by id!");
        }
        return projects.get();
    }

    @Override
    public void updateStatusProject(UpdateStatusProjectRequest request) throws ValidateFiledException {
        Optional<Projects> projects = projectsRepository.findProjectById(request.getIdProject());
        if (projects.isEmpty()){
            throw new NotFoundException("Don't exits projects by id!");
        }
        if (!request.getVisible().equals(Constants.PROJECTS_IS_VISIBLE)
                && !request.getVisible().equals(Constants.PROJECTS_UN_IS_VISIBLE)){
            throw new ValidateFiledException("Don't exits status in project!");
        }
        projects.get().setVisible(request.getVisible());
        projectsRepository.save(projects.get());
    }

    @Override
    public List<FindAllProjectsDto> findAllProjectToDownload() {
        List<FindAllProjectsDto> findAllProjectsDtos = projectsRepository.findAllProjectsToDownload();
        return findAllProjectsDtos;
    }

    @Override
    public Map<String, List<FindAllProjectsDto>> findAllProjectToDownloadTool() {
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        return projectsRepository.findAllProjectToDownloadTool(idsDepartment);
    }

    private void validateDataCreateProjects(CreateProjectsRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<Projects> projectsOptional = projectsRepository.findProjectByName(request.getName());
        if (projectsOptional.isPresent()){
            if (StringUtils.isNotBlank(request.getShortName())) {
                if (request.getShortName().equals(projectsOptional.get().getShortName())){
                    throw new ValidateFiledException("Exits Project by short name");
                }
            }
            throw new ValidateFiledException("Exits Project by name of Project!");
        }

        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Projects> projects = projectsRepository.findProjectByIdParent(request.getParentId());
            if (projects.isEmpty()){
                throw new ValidateFiledException("Don't exits Project by id parent!");
            }
        }
    }
    private Projects contructProjects(CreateProjectsRequest request) {
        Projects projects = new Projects();
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        projects.setIdUserCreated(csvcUser.getIdUser());
        projects.setIdUserModified(csvcUser.getIdUser());
        projects.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        String timeCurrent = String.valueOf(new Date().getTime());
        projects.setTimeCreated(timeCurrent);
        projects.setTimeModified(timeCurrent);
        return projects;
    }

    private Projects validateDataUpdateProjects(UpdateProjectsRequest request) throws ValidateFiledException{
        Optional<Projects> projectsOptional = projectsRepository.findProjectById(request.getIdProject());
        if (projectsOptional.isEmpty()) {
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
            if (projectByIdParent.isEmpty()){
                throw new ValidateFiledException("Don't exits project by id parent!");
            }
        }

        return projectsOptional.get();
    }

    private Projects editProject(Projects projects, UpdateProjectsRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projects.setName(request.getName());
        projects.setShortName(request.getShortName());
        projects.setParent(request.getParentId());
        projects.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        projects.setTimeModified(timeModified);
        projects.setIdUserModified(csvcUser.getIdUser());
        projects.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        return projects;
    }

}
