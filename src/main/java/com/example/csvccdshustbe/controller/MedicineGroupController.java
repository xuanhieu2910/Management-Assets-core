package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Medicine Group Controller", description = "The Medicine Group APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/medicine-group")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class MedicineGroupController {


    @Autowired
    MedicineGroupService medicineGroupService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllMedicineGroup(){
        try {
            return ApiResponseDto.createdWithState(medicineGroupService.findAllMedicineGroupByStatus(Constants.MEDICINE_GROUP_ACTIVE_STATUS),
                    "find all medicine group success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
