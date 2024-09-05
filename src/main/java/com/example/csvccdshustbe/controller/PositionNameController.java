package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.positionName.CreatePositionNameRequest;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameRequest;
import com.example.csvccdshustbe.request.positionName.UpdatePositionNameRequest;
import com.example.csvccdshustbe.service.positionName.PositionNameService;
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

@Tag(name = "PositionName Controller", description = "The Position Name APIs. Contains operations like find all edit, delete etc.")
@RestController
@RequestMapping("/api/v1/position-name")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class PositionNameController {

    @Autowired
    PositionNameService positionNameService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllPosition(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllPositionNameRequest request) {
        try {
            return ApiResponseDto.createdWithState(positionNameService.findAllPositionNameResponseByName(request),
                    "Find all position name success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPositionName(@RequestBody CreatePositionNameRequest request){
        try {
            positionNameService.createPositionName(request);
            return ApiResponseDto.createdWithMessage("Create new position name success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updatePositionName(@RequestBody UpdatePositionNameRequest request){
        try {
            positionNameService.updatePositionName(request);
            return ApiResponseDto.createdWithMessage("Update position name success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deletePositionNameByIdPositionName(@RequestParam("id-positionname") Integer idPositionName){
        try {
            positionNameService.deletePositionNameByIdPositionName(idPositionName);
            return ApiResponseDto.createdWithMessage("Delete position name success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
