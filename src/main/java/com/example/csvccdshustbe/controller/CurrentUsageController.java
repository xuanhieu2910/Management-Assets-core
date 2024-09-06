package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.currentUsage.CreateCurrentUsageRequest;
import com.example.csvccdshustbe.request.currentUsage.UpdateCurrentUsageRequest;
import com.example.csvccdshustbe.service.curentUsage.CurrentUsageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Current usage Controller", description = "The Current usesage APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/current-usage")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class CurrentUsageController {
    @Autowired
    CurrentUsageService currentUsageService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(currentUsageService.findAllCurrentUsage(), "Find all current usesage success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCurrentUsage(@RequestBody CreateCurrentUsageRequest request){
        try {
            currentUsageService.createCurrentUsage(request);
            return ApiResponseDto.createdWithMessage("Create new Current usage success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUsage(@RequestBody UpdateCurrentUsageRequest request){
        try {
            currentUsageService.updateCurrentUsage(request);
            return ApiResponseDto.createdWithMessage("Update Current usage success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteCurrentUsageByIdCU(@RequestParam("id-currentusage") Integer idCurrentUsage){
        try {
            currentUsageService.deleteCurrentUsageByIdCU(idCurrentUsage);
            return ApiResponseDto.createdWithMessage("Delete Current usage success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
