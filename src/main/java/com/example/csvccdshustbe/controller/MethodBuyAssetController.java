package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineType.CreateMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.UpdateMedicineTypeRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.CreateMethodBuyAssetRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.UpdateMethodBuyAssetRequest;
import com.example.csvccdshustbe.service.original.methodBuyAsset.MethodBuyAssetService;
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

@Tag(name = "Method Buy Asset Controller", description = "The method buy asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/method-buy-asset")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class MethodBuyAssetController {

    @Autowired
    MethodBuyAssetService methodBuyAssetService;

    @GetMapping("/find-all-picked")
    public ResponseEntity<?> findAllMethodBuyAssetPicked(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllMethodBuyAssetPickedRequest findAllMethodBuyAssetRequest){
        try {
            return ApiResponseDto.createdWithState(methodBuyAssetService.findAllActiveMethodBuyAssetResponse(findAllMethodBuyAssetRequest),
                    "Find all method buy asset picked success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createMethodBuyAssetService(@RequestBody CreateMethodBuyAssetRequest request){
        try {
            methodBuyAssetService.createMethodBuyAssetService(request);
            return ApiResponseDto.createdWithMessage("Create new method buy asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateMethodBuyAssetService(@RequestBody UpdateMethodBuyAssetRequest request){
        try {
            methodBuyAssetService.updateMethodBuyAssetService(request);
            return ApiResponseDto.createdWithMessage("Update method buy asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteMedicineTypeByIdMedicineType(@RequestParam("id-methodbuyasset") Integer idMethodBuyAsset){
        try {
            methodBuyAssetService.deleteMethodBuyAssetService(idMethodBuyAsset);
            return ApiResponseDto.createdWithMessage("Delete method buy asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
