package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.document.DocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Document Controller", description = "The Document APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/generate-code-document")
    public ResponseEntity<?> generateAutoCodeDocument(){
        try {
            return ApiResponseDto.createdWithState(documentService.generateCodeDocument(),
                    "Generate code document success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/details")
    public ResponseEntity<?> findDocumentDetailsByCodeDocument(@RequestParam("code") String code){
        try {
            return null;
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
