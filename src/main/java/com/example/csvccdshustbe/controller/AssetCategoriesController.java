package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetCategories.*;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesVisibleResponse;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Asset Categories Controller", description = "The Asset Categories APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset-categories")
public class AssetCategoriesController {


    @Autowired
    AssetCategoriesService assetCategoriesService;

    @GetMapping("/find-all-picked")
    public ResponseEntity<?> findAllAssetsCategoriesIsPickedAndVisible(){
        try {
            return ApiResponseDto.createdWithState(assetCategoriesService.findAllAssetCategoriesIsPicked(), "Find all asset categories success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllAssetCategoriesIsVisibleByCodeAndVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetCategoriesByCodeRequest findAllAssetCategoriesRequest){
        try {
            Page<FindAllAssetCategoriesVisibleResponse> responses =
                    assetCategoriesService.findAllAssetCategoriesByCodeNameAndVisible(findAllAssetCategoriesRequest);
            return ApiResponseDto.createdWithState(responses, "Find all asset categories by code success!",
                    HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAssetCategoriesIsVisibleByCodeAndVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentAssetCategoriesRequest request){
        try {
            Page<FindAllAssetCategoriesResponse> responses =
                    assetCategoriesService.findAllAssetCategories(request);
            return ApiResponseDto.createdWithState(responses, "Find all asset categories by code success!",
                    HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createAssetCategories(@RequestBody CreateAssetCategoryRequest request){
        try {
            assetCategoriesService.createAssetCategory(request);
            return ApiResponseDto.createdWithMessage("Create asset category success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateAssetCategory(@RequestBody UpdateAssetCategoryRequest request){
        try {
            assetCategoriesService.updateAssetCategory(request);
            return ApiResponseDto.createdWithMessage("Update asset category success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteAssetCategoryByIdAssetCategory(@RequestParam("id-asset-category") Integer idCurrentUsage){
        try {
            assetCategoriesService.deleteAssetCategoryByIdAssetCategory(idCurrentUsage);
            return ApiResponseDto.createdWithMessage("Delete asset category success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping
    public ResponseEntity<?> findDetailsAssetCategoryByCodeParentCategory(@RequestParam("code") String codeAssetCategory){
        try {
            return ApiResponseDto.createdWithState(
                    assetCategoriesService.findAssetCategoryDetailsResponseByCode(codeAssetCategory),
                    "Find asset category details success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusAssetCategory(@RequestBody UpdateStatusAssetCategory statusAssetCategory) {
        try {
            assetCategoriesService.updateStatusAssetCategory(statusAssetCategory);
            return ApiResponseDto.createdWithMessage("Update status asset category success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
