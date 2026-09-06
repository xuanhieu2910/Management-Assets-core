package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineGroup.CreateMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.FindAllMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.UpdateMedicineGroupRequest;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
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

@Tag(name = "Medicine Group Controller", description = "The Medicine Group APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/medicine-group")
public class MedicineGroupController {


    @Autowired
    MedicineGroupService medicineGroupService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllMedicineGroup(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllMedicineGroupRequest findAllMedicineGroupRequest){
        try {
            return ApiResponseDto.createdWithState(medicineGroupService.findAllMedicineGroup(findAllMedicineGroupRequest),
                    "find all medicine group success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMedicineGroup(@RequestBody CreateMedicineGroupRequest request){
        try {
            medicineGroupService.createMedicineGroup(request);
            return ApiResponseDto.createdWithMessage("Create new medicine group success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateMedicineGroup(@RequestBody UpdateMedicineGroupRequest request){
        try {
            medicineGroupService.updateMedicineGroup(request);
            return ApiResponseDto.createdWithMessage("Update medicine group success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteMedicineTypeByIdMedicineType(@RequestParam("id-medicinegroup") Integer idMedicineGroup){
        try {
            medicineGroupService.deleteMedicineGroupByIdMedicineGroup(idMedicineGroup);
            return ApiResponseDto.createdWithMessage("Delete medicine group success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
