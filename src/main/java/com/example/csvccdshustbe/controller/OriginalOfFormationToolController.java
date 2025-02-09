package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.originalOfFormationTool.CreateOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.UpdateOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.UpdateStatusOriginalOfFormationToolRequest;
import com.example.csvccdshustbe.service.originalOfFormationTool.OriginalOfFormationToolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Original of formation tool Controller", description = "The Original of formation tool APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/original-of-formation-tool")
public class OriginalOfFormationToolController {

    @Autowired
    OriginalOfFormationToolService originalOfFormationToolService;


    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllOriginalOfFormationToolVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllOriginalOfFormationToolVisibleRequest request){
        try {
            return ApiResponseDto.createdWithState(originalOfFormationToolService.findAllOriginalOfFormationToolVisible(request),
                    "Find all original of formation tool success", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createOriginalOfFormationToolService(@RequestBody CreateOriginalOfFormationToolRequest request){
        try {
            originalOfFormationToolService.createOriginalOfFormationToolService(request);
            return ApiResponseDto.createdWithMessage("Create new original of formation tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateOriginalOfFormationToolService(@RequestBody UpdateOriginalOfFormationToolRequest request){
        try {
            originalOfFormationToolService.updateOriginalOfFormationToolService(request);
            return ApiResponseDto.createdWithMessage("Update original of formation tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteOriginalOfFormationToolServiceById(@RequestParam("id-original-of-formation") Integer idDepartment){
        try {
            originalOfFormationToolService.deleteOriginalOfFormationToolServiceById(idDepartment);
            return ApiResponseDto.createdWithMessage("Delete original of formation tool success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusOriginalOfFormationToolService(@RequestBody UpdateStatusOriginalOfFormationToolRequest request){
        try {
            originalOfFormationToolService.updateStatusOriginalOfFormationToolService(request);
            return ApiResponseDto.createdWithMessage("Update status original of formation tool success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
