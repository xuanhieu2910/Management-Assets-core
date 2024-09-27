package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.projects.CreateProjectsRequest;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateProjectsRequest;
import com.example.csvccdshustbe.request.projects.UpdateStatusProjectRequest;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Projects Controller", description = "The Projects APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {

    @Autowired
    ProjectsService projectsService;


    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllProjectsVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProjectsRequest request) {
        try {
            return ApiResponseDto.createdWithState(projectsService.findAllProjectsVisibleResponse(request),
                    "Find all projects success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllProjects(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProjectsRequest request) {
        try {
            return ApiResponseDto.createdWithState(projectsService.findAllProjectsResponse(request),
                    "Find all projects success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectsRequest request){
        try {
            projectsService.createProject(request);
            return ApiResponseDto.createdWithMessage("Create new project success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateProject(@RequestBody UpdateProjectsRequest request){
        try {
            projectsService.updateProject(request);
            return ApiResponseDto.createdWithMessage("Update project success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteProjectByIdProject(@RequestParam("id-project") Integer idProject){
        try {
            projectsService.deleteProjectByIdProject(idProject);
            return ApiResponseDto.createdWithMessage("Delete project success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusProject(@RequestBody UpdateStatusProjectRequest request){
        try {
            projectsService.updateStatusProject(request);
            return ApiResponseDto.createdWithMessage("Update status project success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}