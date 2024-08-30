package com.example.csvccdshustbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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

//    @GetMapping("/find-all")
//    public ResponseEntity<?> findAllMedicineGroup(){
//
//    }
}
