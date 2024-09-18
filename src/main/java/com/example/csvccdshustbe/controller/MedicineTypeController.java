package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineType.*;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
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

@Tag(name = "Medicine Type Controller", description = "The Medicine Type APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/medicine-type")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class MedicineTypeController {

    @Autowired
    MedicineTypeService medicineTypeService;

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllTypeMedicineVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllMedicineTypeVisibleRequest request){
        try{
            return ApiResponseDto.createdWithState(medicineTypeService.findAllMedicineTypeVisibleResponse(request),
                    "Find all medicine type success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllTypeMedicineVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllMedicineTypeRequest request){
        try{
            return ApiResponseDto.createdWithState(medicineTypeService.findAllMedicineTypeResponse(request),
                    "Find all medicine type success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createMedicineType(@RequestBody CreateMedicineTypeRequest request){
        try {
            medicineTypeService.createMedicineType(request);
            return ApiResponseDto.createdWithMessage("Create new medicine type success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateMedicineType(@RequestBody UpdateMedicineTypeRequest request){
        try {
            medicineTypeService.updateMedicineType(request);
            return ApiResponseDto.createdWithMessage("Update medicine type success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteMedicineTypeByIdMedicineType(@RequestParam("id-medicinetype") Integer idMedicineType){
        try {
            medicineTypeService.deleteMedicineTypeByIdMedicineType(idMedicineType);
            return ApiResponseDto.createdWithMessage("Delete medicine type success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusMedicine(@RequestBody UpdateMedicineStatusRequest request){
        try {
            medicineTypeService.updateStatusMedicine(request);
            return ApiResponseDto.createdWithMessage("Update status success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
