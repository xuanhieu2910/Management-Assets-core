package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetProcess.AssetNotDeclareWhenInventoryRequest;
import com.example.csvccdshustbe.request.assetProcess.AssetProcessRequest;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.request.assetProcess.UpdateAssetProcessRequest;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Asset Process Controller", description = "The Asset Process APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset-process")
public class AssetProcessController {

    @Autowired
    AssetProcessService assetProcessService;



    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAssetProcess(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetProcess(request),
                    "Find all data asset document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-update-inventory")
    public ResponseEntity<?> findAllAssetUpdateInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request) {
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetUpdateInventoryProcess(request),
                    "Find all data asset process document success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all-lot-update-inventory")
    public ResponseEntity<?> findAllAssetLotUpdateInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request) {
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetProcessLotUpdateInventoryProcess(request),
                    "Find all data asset process lot to update inventory success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-all-lot")
    public ResponseEntity<?> findAllAssetLotProcess(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetLotProcess(request),
                    "Find all data asset lot document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children")
    public ResponseEntity<?> findAllAssetChildren(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetChildrenProcess(request),
                    "Find all data asset children document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create-new-asset-inventory")
    public ResponseEntity<?>
    createNewAssetNotDeclareWhenInventory(@RequestBody AssetNotDeclareWhenInventoryRequest request){
        try {
            return ApiResponseDto.createdWithState(assetProcessService.createNewAssetNotDeclareWhenInventory(request),
                    "Create new asset not declare when inventory success!",
                    HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-asset-inventory")
    public ResponseEntity<?> updateAssetProcessInventory(@RequestBody UpdateAssetProcessRequest request){
        try {
            assetProcessService.updateAssetProcessInventory(request);
            return ApiResponseDto.createdWithMessage("Update asset inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
