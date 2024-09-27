package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.originalOfFormation.*;
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
public class OriginalOfFormationController {


    @Autowired
    OriginalOfFormationService originalOfFormationService;

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllOriginalOfFormationVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllOriginalOfFormationVisibleRequest request){
        try {
            return ApiResponseDto.createdWithState(originalOfFormationService.findAllOriginalOfFormationVisible(request),
                    "Find all original of formation success", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/find-all")
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
    public ResponseEntity<?> createOriginalOfFormationService(@RequestBody CreateOriginalOfFormationRequest request){
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
    public ResponseEntity<?> deleteOriginalOfFormationServiceById(@RequestParam("id-original-of-formation") Integer idDepartment){
        try {
            originalOfFormationService.deleteOriginalOfFormationServiceById(idDepartment);
            return ApiResponseDto.createdWithMessage("Delete original of formation success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusOriginalOfFormation(@RequestBody UpdateStatusOriginalOfFormationRequest request){
        try {
            originalOfFormationService.updateStatusOriginalOfFormation(request);
            return ApiResponseDto.createdWithMessage("Update status original of formaiton success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
