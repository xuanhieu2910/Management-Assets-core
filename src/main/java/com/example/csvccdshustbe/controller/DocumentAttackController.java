package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Documents Attack Controller", description = "The Units APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/document-attack")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class DocumentAttackController {


    @Autowired
    DocumentAttackService documentAttackService;
    public DocumentAttackController(DocumentAttackService documentAttackService) {
        this.documentAttackService = documentAttackService;
    }
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(
                    documentAttackService.findAllDocumentAttackResponseByStatus(Constants.DOCUMENT_ATTACK_ACTIVE_STATUS), "Find all Document Attack success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("{documentAttackId}")
    public DocumentAttack getDocumentAttackDetails(@PathVariable("documentAttackId") Integer documentAttackId){
        return documentAttackService.getDocumentAttack(documentAttackId);
    }
    @PostMapping
    public String createDocumentAttackDetails(@RequestBody DocumentAttack documentAttack){
        documentAttackService.createDocumentAttack(documentAttack);
        return "Document attack create success";
    }
    @PutMapping
    public String updateDocumentAttackDetails(@RequestBody DocumentAttack documentAttack){
        documentAttackService.updateDocumentAttack(documentAttack);
        return "Document attack update success";
    }
    @DeleteMapping("{documentAttackId}")
    public String deleteDocumentAttackDetails(@PathVariable("documentAttackId") Integer documentAttackId){
        documentAttackService.deleteDocumentAttack(documentAttackId);
        return "Delete success";
    }
}
