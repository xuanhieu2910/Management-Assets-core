package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.Api;
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
        } catch (ValidateFiledException | JsonProcessingException e ){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAsset(@RequestBody HashMap<String,Object> updateAssetRequest){
        try {
            assetService.updateAsset(updateAssetRequest);
            return ApiResponseDto.createdWithMessage("Update asset success!", HttpStatus.OK);
        }catch (ValidateFiledException | JsonProcessingException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping
    public ResponseEntity<?> findAssetByCode(@RequestParam("code") String codeAsset){
        try {
            return ApiResponseDto.createdWithState(assetService.findDetailsAssetByCodeAsset(codeAsset),
                    "Find asset details success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetRequest findAllAssetRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAsset(findAllAssetRequest),
                    "Find all asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAsset(@RequestParam("code-asset") String codeAsset){
        try {
            assetService.deleteAssetByCodeAsset(codeAsset);
            return ApiResponseDto.createdWithMessage("Delete asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
