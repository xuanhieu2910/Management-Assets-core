package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.FinaAllAssetToIncreaseRequest;
import com.example.csvccdshustbe.request.asset.FindAllAssetDocumentRequest;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.utility.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.HashMap;

@Log4j2
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
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create-lot")
    public ResponseEntity<?> createAssetLot(@RequestBody HashMap<String, Object> createAssetRequest){
        try{
            assetService.createAsset(createAssetRequest);
            return ApiResponseDto.createdWithMessage("Create asset success!", HttpStatus.OK);
        } catch (ValidateFiledException | JsonProcessingException e ){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateAsset(@RequestBody HashMap<String,Object> updateAssetRequest){
        try {
            assetService.updateAsset(updateAssetRequest);
            return ApiResponseDto.createdWithMessage("Update asset success!", HttpStatus.OK);
        }catch (ValidateFiledException | JsonProcessingException | NotFoundException e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping
    public ResponseEntity<?> findAssetByCode(@RequestParam("code") String codeAsset){
        try {
            return ApiResponseDto.createdWithState(assetService.findDetailsAssetByCodeAsset(codeAsset),
                    "Find asset details success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
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


//    @PostMapping("/upload-file")
//    public ResponseEntity<?> uploadFiles(@RequestParam("file")MultipartFile multipartFile){
//        try {
//            return ApiResponseDto.createdWithState(assetService.uploadFile(multipartFile),
//                    "Upload file success!", HttpStatus.OK);
//        } catch (Exception e){
//            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

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
            return ApiResponseDto.createdWithState(assetService.downloadFileTemplateImportAsset(),
                    "Download file template import asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFileAssetToSystem(@RequestParam("file")MultipartFile file){
        try {
            assetService.uploadFileImportAsset(file);
            return ApiResponseDto.createdWithMessage("Upload file asset success!", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-all-to-increase")
    public ResponseEntity<?> findAllToIncreaseAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FinaAllAssetToIncreaseRequest request){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetToIncrease(request), "Find all asset to increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/assets-document")
    public ResponseEntity<?> findAllAssetByCodeDocument(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetDocumentRequest request){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetDocumentByCodeDocument(request),
                    "Find all data asset document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/generate-code")
    public ResponseEntity<?> generateCodeAsset(){
        try {
            return ApiResponseDto.createdWithState(assetService.generateCodeAsset(Constants.PREFIX_ASSET),
                    "Generate asset code success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/generate-code-lot")
    public ResponseEntity<?> generateCodeAssetLot(){
        try {
            return ApiResponseDto.createdWithState(assetService.generateCodeAsset(Constants.PREFIX_ASSET_LOT),
                    "Generate asset code success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
