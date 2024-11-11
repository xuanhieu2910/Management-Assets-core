package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetInstance.CreateAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.DeleteAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.UpdateAssetInstanceRequest;
import com.example.csvccdshustbe.service.assetInstance.AssetInstanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Log4j2
@Tag(name = "Asset Instance Controller", description = "The Asset Instance APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset-instance")
public class AssetInstanceController {


    @Autowired
    AssetInstanceService assetInstanceService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getAssetInstance(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetInstanceRequest request){
        try {
            return ApiResponseDto.createdWithState(assetInstanceService.findAllAssetInstance(request),
                    "Find all asset instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/update-asset-instance")
    public ResponseEntity<?> updateAssetInstance(@RequestBody UpdateAssetInstanceRequest request){
        try {
            assetInstanceService.updateAssetInstance(request);
            return ApiResponseDto.createdWithMessage("Update asset instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAssetInstance(@RequestBody DeleteAssetInstanceRequest request){
        try {
            assetInstanceService.deleteAssetInstance(request);
            return ApiResponseDto.createdWithMessage("Update asset instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create-assets")
    public ResponseEntity<?> createAssets(@RequestBody CreateAssetInstanceRequest request){
        try {
            assetInstanceService.createAssetInstance(request);
            return ApiResponseDto.createdWithMessage("Update asset instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
