package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.levelTypeAsset.CreateLevelTypeAssetRequest;
import com.example.csvccdshustbe.request.levelTypeAsset.UpdateLevelTypeAssetRequest;
import com.example.csvccdshustbe.service.levelTypeAsset.LevelTypeAssetService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Level type Asset Controller", description = "The Level type asset APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/level-type-asset")
public class LevelTypeAssetController {
    @Autowired
    LevelTypeAssetService levelTypeAssetService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(
                    levelTypeAssetService.findAllLevelTypeAssetResponseByStatus(Constants.LEVEL_TYPE_ASSET_ACTIVE_STATUS),
                    "Find all Level type asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLevelTypeAsset(@RequestBody CreateLevelTypeAssetRequest request){
        try {
            levelTypeAssetService.createLevelTypeAsset(request);
            return ApiResponseDto.createdWithMessage("Create new Level Types Asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateLevelTypeAsset(@RequestBody UpdateLevelTypeAssetRequest request){
        try {
            levelTypeAssetService.updateLevelTypeAsset(request);
            return ApiResponseDto.createdWithMessage("Update Level Types Asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteSuppliersByIdSuppliers(@RequestParam("id-LevelTypeAsset") Integer idLevelTypeAsset){
        try {
            levelTypeAssetService.deleteLevelTypeAssetsByIdLTA(idLevelTypeAsset);
            return ApiResponseDto.createdWithMessage("Delete Level Types Asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
