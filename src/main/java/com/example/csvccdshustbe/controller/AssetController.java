package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "Asset Controller", description = "The Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class AssetController {

    @Autowired
    AssetService assetService;


    @PostMapping("/create")
    public ResponseEntity<?> createAsset(@RequestBody HashMap<String, Object> createAssetRequest){
        try{
            assetService.createAsset(createAssetRequest);
            return ApiResponseDto.createdWithMessage("Create asset success!", HttpStatus.OK);
        }catch (ValidateFiledException e ){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<?> updateAsset(){
//
//    }

}
