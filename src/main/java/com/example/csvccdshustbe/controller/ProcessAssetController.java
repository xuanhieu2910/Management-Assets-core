package com.example.csvccdshustbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Process Asset Controller", description = "The Process Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/process-asset")
public class ProcessAssetController {

    @PostMapping("/increase")
    public ResponseEntity<?> increaseAsset(){
        return null;
    }

}
