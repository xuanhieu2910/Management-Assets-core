package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.department.CreateDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.request.department.UpdateDepartmentRequest;
import com.example.csvccdshustbe.response.department.FindAllDepartmentVisibleResponse;
import com.example.csvccdshustbe.service.department.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Asset Departments Controller", description = "The Department APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/department")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class DepartmentController {


    @Autowired
    DepartmentService departmentService;

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllDepartmentIsVisibleByCodeAndVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDepartmentVisibleRequest findAllDepartmentRequest){
        try {
            Page<FindAllDepartmentVisibleResponse> responses = departmentService.findAllDepartmentVisibleByCodeAndVisible(findAllDepartmentRequest);
            return ApiResponseDto.createdWithState(responses, "Find all asset categories by code success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllDepartment(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDepartmentRequest request){
        try {
            return ApiResponseDto.createdWithState(departmentService.findAllDepartment(request),
                    "Find all department success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestBody CreateDepartmentRequest request){
        try {
            departmentService.createDepartment(request);
            return ApiResponseDto.createdWithMessage("Create new department success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestBody UpdateDepartmentRequest request){
        try {
            departmentService.updateDepartment(request);
            return ApiResponseDto.createdWithMessage("Update department success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteDepartmentByIdDepartment(@RequestParam("id-department") Integer idDepartment){
        try {
            departmentService.deleteDepartmentByIdDepartment(idDepartment);
            return ApiResponseDto.createdWithMessage("Delete department success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
