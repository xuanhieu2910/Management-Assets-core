package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.*;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.upload.impl.FileUploadService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.FileUtil;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    FileUploadService fileUploadService;



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

    @PostMapping("/create-lot")
    public ResponseEntity<?> createAssetLot(@RequestBody HashMap<String, Object> createAssetRequest){
        try{
            assetService.createAssetLot(createAssetRequest);
            return ApiResponseDto.createdWithMessage("Create asset lot success!", HttpStatus.OK);
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
        }catch (ValidateFiledException | JsonProcessingException | NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-lot")
    public ResponseEntity<?> updateAssetLot(@RequestBody HashMap<String,Object> updateAssetRequest){
        try {
            assetService.updateAssetLot(updateAssetRequest);
            return ApiResponseDto.createdWithMessage("Update asset lot success!", HttpStatus.OK);
        } catch (ValidateFiledException | JsonProcessingException | NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }



    @GetMapping
    public ResponseEntity<?> findAssetBySalt(@RequestParam("salt") String saltAsset){
        try {
            return ApiResponseDto.createdWithState(assetService.findDetailsAssetBySaltAsset(saltAsset),
                    "Find asset details success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/children")
    public ResponseEntity<?> getAssetChildrenBySaltParent(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetLotChildrenRequest findAllAssetRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetLotChildren(findAllAssetRequest),
                    "Find all asset lot children success!", HttpStatus.OK);
        } catch (NotFoundException e){
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
    public ResponseEntity<?> deleteAsset(@RequestParam("salt-asset") String saltAsset){
        try {
            assetService.deleteAssetBySaltAsset(saltAsset);
            return ApiResponseDto.createdWithMessage("Delete asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/asset-lot")
    public ResponseEntity<?> deleteAssetLot(@RequestParam("salt-asset") String saltAsset){
        try {
            assetService.deleteAssetBySaltAssetLot(saltAsset);
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

    @GetMapping("/download-file-template-import-asset")
    public ResponseEntity<?> downloadFileTemplateImportAsset(){
        try {
            return ApiResponseDto.createdWithState(assetService.downloadFileTemplateImportAsset(),
                    "Download file template import asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload-file-import-asset")
    public ResponseEntity<?> uploadFileAssetToSystem(@RequestParam("file")MultipartFile file){
        try {
            assetService.uploadFileImportAsset(file);
            return ApiResponseDto.createdWithMessage("Upload file asset success!", HttpStatus.OK);
        }catch (Exception e){
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
            return ApiResponseDto.createdWithState(assetService.findAllAssetToIncrease(request),
                    "Find all asset to increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children-to-increase")
    public ResponseEntity<?> findAllChildrenToIncreaseAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FinaAllAssetToIncreaseRequest request){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetChildrenToIncrease(request),
                    "Find all asset children to increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all-to-inventory")
    public ResponseEntity<?> findAllToInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToInventoryRequest inventoryRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetToInventory(inventoryRequest),
                    "Find all asset to inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children-to-inventory")
    public ResponseEntity<?> findAllChildrenToInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToInventoryRequest inventoryRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetChildrenToInventory(inventoryRequest),
                    "Find all asset to inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-to-change")
    public ResponseEntity<?> findAllToChange(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToChangeRequest changeRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetToChange(changeRequest),
                    "Find all asset to change success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-to-revaluation")
    public ResponseEntity<?> findAllToRevaluation(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToRevaluationRequest revaluationRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetToRevaluation(revaluationRequest),
                    "Find all asset to revaluation success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children-to-revaluation")
    public ResponseEntity<?> findAllChildrenToRevaluation(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToRevaluationRequest revaluationRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetChildrenToRevaluation(revaluationRequest),
                    "Find all asset children to revaluation success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all-to-decrease")
    public ResponseEntity<?> findAllToDecrease(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToDecreaseRequest decreaseRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetToDecrease(decreaseRequest),
                    "Find all asset to decrease success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children-to-decrease")
    public ResponseEntity<?> findAllChildrenToDecrease(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetToDecreaseRequest decreaseRequest){
        try {
            return ApiResponseDto.createdWithState(assetService.findAllAssetChildrenToDecrease(decreaseRequest),
                    "Find all asset children to decrease success!", HttpStatus.OK);
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


    @PostMapping("/upload-files-attached")
    public ResponseEntity<?> uploadFilesAttached(@RequestParam("files") MultipartFile[] files){
        try {
            String pathUploadFilesAttached = fileUploadService.updateFilesAttached(files, FileUtil.FOLDER_ASSET);
            return ApiResponseDto.createdWithState(pathUploadFilesAttached, "Upload files attached success!",
                    HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete-file-attached")
    public ResponseEntity<?>deleteFileAttached(@RequestParam("name") String pathFile){
        try {
            fileUploadService.deleteByPathFile(pathFile,
                    PropertiesUtil.getProperty("hust.csvc.static.location.path.static.upload.data"),
                    PropertiesUtil.getProperty("hust.csvc.static.location.upload.data"));
            return ApiResponseDto.createdWithMessage("Delete path file success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/statistics-asset")
    public  ResponseEntity<?> getStatisticFindAllAsset(){
        try {
            return ApiResponseDto.createdWithState(assetService.getStatisticFindAllAsset(),
                    "Get statistic find all asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/statistics-dashboard-asset")
    public  ResponseEntity<?> getStatisticAssetAndUserFindAllAsset(){
        try {
            return ApiResponseDto.createdWithState(assetService.getStatisticAssetAndUserFindAllAsset(),
                    "Get statistic asset in category success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/statistics-dashboard-status-use-asset")
    public  ResponseEntity<?> getStatisticAssetCategoryStatusUse(@RequestParam("code-category") String codeCategory){
        try {
            return ApiResponseDto.createdWithState(assetService.getStatisticAssetCategoryStatusUse(codeCategory),
                    "Get statistic dashboard status asset inc category success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
