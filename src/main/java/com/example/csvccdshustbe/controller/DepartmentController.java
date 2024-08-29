package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.response.department.FindAllDepartmentResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Asset Categories Controller", description = "The Asset Categories APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/department")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class DepartmentController {


    @Autowired
    DepartmentService departmentService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllDepartmentIsVisibleByCodeAndVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDepartmentRequest findAllDepartmentRequest){
        try {
            Page<FindAllDepartmentResponse> responses = departmentService.findAllDepartmentByCodeAndVisible(findAllDepartmentRequest);
            return ApiResponseDto.createdWithState(responses, "Find all asset categories by code success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
