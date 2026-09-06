package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.assetCategories.CreateAssetCategoryRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllDocumentAssetCategoriesRequest;
import com.example.csvccdshustbe.request.assetCategories.UpdateAssetCategoryRequest;
import com.example.csvccdshustbe.request.assetCategories.UpdateStatusAssetCategory;
import com.example.csvccdshustbe.request.toolCategories.CreateToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateStatusToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateToolCategoryRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.response.toolCategories.FindAllToolCategoriesResponse;
import com.example.csvccdshustbe.service.toolCategories.ToolCategoriesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Tool Categories Controller", description = "The Tool Categories APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/tool-categories")
public class ToolCategoriesController {
    @Autowired
    ToolCategoriesService toolCategoriesService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllToolCategories(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllToolCategoriesRequest request){
        try {
            Page<FindAllToolCategoriesResponse> responses =
                    toolCategoriesService.findAllToolCategories(request);
            return ApiResponseDto.createdWithState(responses, "Find all tool categories success!",
                    HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createToolCategory(@RequestBody CreateToolCategoryRequest request){
        try {
            toolCategoriesService.createToolCategory(request);
            return ApiResponseDto.createdWithMessage("Create tool category success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateToolCategory(@RequestBody UpdateToolCategoryRequest request){
        try {
            toolCategoriesService.updateToolCategory(request);
            return ApiResponseDto.createdWithMessage("Update tool category success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteToolCategoryByIdAssetCategory(@RequestParam("id-tool-category") Integer idCurrentUsage){
        try {
            toolCategoriesService.deleteToolCategoryByIdAssetCategory(idCurrentUsage);
            return ApiResponseDto.createdWithMessage("Delete tool category success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping
    public ResponseEntity<?> findDetailsAssetCategoryByCodeParentCategory(@RequestParam("id-tool-category") Integer idToolCategory){
        try {
            return ApiResponseDto.createdWithState(
                    toolCategoriesService.findToolCategoryDetailsResponse(idToolCategory),
                    "Find tool category details success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusAssetCategory(@RequestBody UpdateStatusToolCategoryRequest request) {
        try {
            toolCategoriesService.updateStatusToolCategory(request);
            return ApiResponseDto.createdWithMessage("Update status tool category success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
