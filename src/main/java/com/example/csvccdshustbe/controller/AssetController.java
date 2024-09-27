package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.HashMap;

@Tag(name = "Asset Controller", description = "The Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset")
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


    @GetMapping("/ground-asset/find-all")
    public ResponseEntity<?> findAllAssetGround(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllGroundAssetRequest request){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllGroundAsset(request),
                    "Find all ground asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFiles(@RequestParam("file")MultipartFile multipartFile){
        try {
            return ApiResponseDto.createdWithState(assetService.uploadFile(multipartFile),
                    "Upload file success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete-file")
    public ResponseEntity<?> deleteFiles(@RequestParam("path-file") String pathFile){
        try {
            assetService.deleteFile(pathFile);
            return ApiResponseDto.createdWithMessage("Delete file success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-file-template-import-asset")
    public ResponseEntity<?> downloadFileTemplateImportAsset(){
        try {
            Resource resource = assetService.downloadFileTemplateImportAsset();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
