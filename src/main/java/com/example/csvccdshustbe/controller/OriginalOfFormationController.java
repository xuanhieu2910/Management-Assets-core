package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.department.CreateDepartmentRequest;
import com.example.csvccdshustbe.request.department.UpdateDepartmentRequest;
import com.example.csvccdshustbe.request.originalOfFormation.CreateOriginalOfFormationReuqest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateOriginalOfFormationRequest;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
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

@Tag(name = "Original of formation Controller", description = "The Original of formation APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/original-of-formation")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class OriginalOfFormationController {


    @Autowired
    OriginalOfFormationService originalOfFormationService;

    @GetMapping
    public ResponseEntity<?> findAllOriginalOfFormation(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllOriginalOfFormationRequest request){
        try {
            return ApiResponseDto.createdWithState(originalOfFormationService.findAllOriginalOfFormation(request),
                    "Find all original of formation success", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

    }


    @PostMapping("/create")
    public ResponseEntity<?> createOriginalOfFormationService(@RequestBody CreateOriginalOfFormationReuqest request){
        try {
            originalOfFormationService.createOriginalOfFormationService(request);
            return ApiResponseDto.createdWithMessage("Create new original of formation success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateOriginalOfFormationService(@RequestBody UpdateOriginalOfFormationRequest request){
        try {
            originalOfFormationService.updateOriginalOfFormationService(request);
            return ApiResponseDto.createdWithMessage("Update original of formation success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteOriginalOfFormationServiceById(@RequestParam("id-originalofformation") Integer idDepartment){
        try {
            originalOfFormationService.deleteOriginalOfFormationServiceById(idDepartment);
            return ApiResponseDto.createdWithMessage("Delete original of formation success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
