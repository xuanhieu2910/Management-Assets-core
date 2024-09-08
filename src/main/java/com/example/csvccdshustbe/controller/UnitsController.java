package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.units.CreateUnitsRequest;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import com.example.csvccdshustbe.request.units.UpdateUnitsRequest;
import com.example.csvccdshustbe.service.units.UnitsService;
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

@Tag(name = "Units Controller", description = "The Units APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/units")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UnitsController {


    @Autowired
    UnitsService unitsService;



    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(unitsService.findAllUnits(), "Find all units success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-by-asset-category")
    public ResponseEntity<?> findAllUnitByCodeNameAssetCategory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllUnitsByAssetCategoryRequest findAllUnitsByAssetCategoryRequest){
        try {
            return ApiResponseDto.createdWithState(unitsService.findAllUnitsByCodeAssetCategoryResponse(findAllUnitsByAssetCategoryRequest),
                    "Find all units by code name!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }



    @PostMapping("/create")
    public ResponseEntity<?> createUnits(@RequestBody CreateUnitsRequest request){
        try {
            unitsService.createUnits(request);
            return ApiResponseDto.createdWithMessage("Create new Unit success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUnits(@RequestBody UpdateUnitsRequest request){
        try {
            unitsService.updateUnits(request);
            return ApiResponseDto.createdWithMessage("Update Unit success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUnitsByIdUnits(@RequestParam("id-unit") Integer idUnit){
        try {
            unitsService.deleteUnitsByIdUnits(idUnit);
            return ApiResponseDto.createdWithMessage("Delete Unit success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
