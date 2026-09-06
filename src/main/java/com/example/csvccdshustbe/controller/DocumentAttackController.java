package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.documentAttack.*;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
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

@Tag(name = "Documents Attack Controller", description = "The Document attack APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/document-attack")
public class DocumentAttackController {


    @Autowired
    DocumentAttackService documentAttackService;


    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAll(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentAttackVisibleRequest findAllDocumentAttackRequest){
        try {
            return ApiResponseDto.createdWithState(
                    documentAttackService.findAllDocumentAttackVisibleResponse(findAllDocumentAttackRequest),
                    "Find all Document Attack success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentAttackRequest findAllDocumentAttackRequest){
        try {
            return ApiResponseDto.createdWithState(
                    documentAttackService.findAllDocumentAttackResponse(findAllDocumentAttackRequest),
                    "Find all Document Attack success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createDocumentAttack(@RequestBody CreateDocumentAttackRequest request){
        try {
            documentAttackService.createDocumentAttack(request);
            return ApiResponseDto.createdWithMessage("Create new document attack success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateDocumentAttack(@RequestBody UpdateDocumentAttackRequest request){
        try {
            documentAttackService.updateDocumentAttack(request);
            return ApiResponseDto.createdWithMessage("Update document attack success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteDocumentAttackByIdDA(@RequestParam("id-document-attack") Integer idDepartment){
        try {
            documentAttackService.deleteDocumentAttackByIdDA(idDepartment);
            return ApiResponseDto.createdWithMessage("Delete document attack  success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatusDocumentAttack(@RequestBody UpdateStatusDocumentAttackRequest request){
        try {
            documentAttackService.updateStatusDocumentAttack(request);
            return ApiResponseDto.createdWithMessage("Delete document attack  success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
