package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.document.UpdateInventoryDraftRequest;
import com.example.csvccdshustbe.request.document.tool.FindAllDocumentToolRequest;
import com.example.csvccdshustbe.request.process.*;
import com.example.csvccdshustbe.request.process.tool.CreateDecreaseToolRequest;
import com.example.csvccdshustbe.request.process.tool.CreateIncreaseToolRequest;
import com.example.csvccdshustbe.request.process.tool.CreateInventoryToolRequest;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;


@Log4j2
@Tag(name = "Document Controller", description = "The Document APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @Autowired
    ProcessService processService;
    @GetMapping("/generate-code-document")
    public ResponseEntity<?> generateAutoCodeDocumentAsset(@RequestParam("tg") String typeGenerate){
        try {
            return ApiResponseDto.createdWithState(documentService.generateCodeDocument(typeGenerate),
                    "Generate code document success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/details")
    public ResponseEntity<?> findDocumentDetailsByCodeDocument(@RequestParam("code") String code){
        try {
            return ApiResponseDto.createdWithState(documentService.findDetailsDocumentByCodeDocument(code),
                    "Find details document success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-find-all")
    public ResponseEntity<?> findAllProcessAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentAssetRequest findAllDocumentAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDocumentByAsset(findAllDocumentAssetRequest),
                    "Find all document success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-find-all-increase")
    public ResponseEntity<?> findAllProcessAssetIncrease(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetIncreaseRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDataProcessAssetIncrease(findAllProcessAssetRequest),
                    "Find all document increase success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-find-all-document-inventory")
    public ResponseEntity<?> findAllProcessAssetInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetDocumentInventoryRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDataProcessAssetDocumentInventory(findAllProcessAssetRequest),
                    "Find all document inventory success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/asset-find-all-update-inventory")
    public ResponseEntity<?> findAllUpdateInventoryAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetUpdateInventoryRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDataProcessAssetUpdateInventory(findAllProcessAssetRequest),
                    "Find all update inventory success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-find-all-decrease")
    public ResponseEntity<?> findAllProcessAssetInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetDecreaseRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDataProcessAssetDecrease(findAllProcessAssetRequest),
                    "Find all document decrease success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/asset-find-all-change")
    public ResponseEntity<?> findAllProcessAssetInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetChangeRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(
                    documentService.findAllDataProcessAssetChange(findAllProcessAssetRequest),
                    "Find all document change success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/asset-find-all-revaluation")
    public ResponseEntity<?> findAllProcessAssetInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetRevaluationRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(
                    documentService.findAllDataProcessAssetRevaluation(findAllProcessAssetRequest),
                    "Find all document revaluation success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/asset-increase")
    public ResponseEntity<?> increaseAsset(@RequestBody CreateIncreaseAssetRequest request){
        try {
            processService.createIncreaseAsset(request);
            return ApiResponseDto.createdWithMessage("Create increase asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/asset-document-inventory")
    public ResponseEntity<?> createDocumentInventoryAsset(@RequestBody CreateInventoryAssetRequest request){
        try {
            processService.createDocumentInventoryAsset(request);
            return ApiResponseDto.createdWithMessage("Create inventory asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/asset-decrease")
    public ResponseEntity<?> decreaseAsset(@RequestBody CreateDecreaseAssetRequest request){
        try {
            processService.createDecreaseAsset(request);
            return ApiResponseDto.createdWithMessage("Create decrease asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/asset-change")
    public ResponseEntity<?> changeAsset(@RequestBody CreateChangeAssetRequest request){
        try {
            processService.createChangeAsset(request);
            return ApiResponseDto.createdWithMessage("Create change asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/asset-revaluation")
    public ResponseEntity<?> revaluationAsset(@RequestBody CreateRevaluationAssetRequest request){
        try {
            processService.createRevaluationAsset(request);
            return ApiResponseDto.createdWithMessage("Create revaluation asset success!", HttpStatus.OK);
        } catch (ValidateFiledException | JsonProcessingException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/asset-be-assigned")
    public ResponseEntity<?> findAllProcessBeAssignedAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessBeAssignedRequest request){
        try {
            return ApiResponseDto.createdWithState(processService.findAllProcessBeAssignedResponse(request),
                    "Find all process be assigned success!", HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-be-assigned-document-inventory")
    public ResponseEntity<?> findAllProcessBeAssignedInventoryAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessBeAssignedDocumentInventoryRequest request){
        try {
            return ApiResponseDto.createdWithState(processService.findAllProcessBeAssignedDocumentInventoryResponse(request),
                    "Find all process be assigned document inventory success!", HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-increase")
    public ResponseEntity<?> getStatisticIncreaseAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticIncrease(),
                    "Get statistic increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-update-inventory")
    public ResponseEntity<?> getStatisticUpdateInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticUpdateInventory(),
                    "Get statistic update inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-document-inventory")
    public ResponseEntity<?> getStatisticDocumentInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticDocumentInventory(),
                    "Get statistic inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-document-be-inventory")
    public ResponseEntity<?> getStatisticDocumentBeInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticDocumentBeInventory(),
                    "Get statistic be inventory success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/asset-statistic-decrease")
    public ResponseEntity<?> getStatisticDecreaseAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticDecrease(),
                    "Get statistic decrease success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-change")
    public ResponseEntity<?> getStatisticChangeAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticChange(),
                    "Get statistic change success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/asset-statistic-revaluation")
    public ResponseEntity<?> getStatisticRevaluationAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticRevaluation(),
                    "Get statistic revaluation success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/asset-update-inventory-draft")
    public ResponseEntity<?> updateInventoryDraftAsset(@RequestBody UpdateInventoryDraftRequest request){
        try {
            documentService.updateInventoryDraft(request);
            return ApiResponseDto.createdWithMessage("Update inventory draft success!", HttpStatus.OK);
        } catch (NotFoundException e){
          return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/asset-update-inventory-finish")
    public ResponseEntity<?> finishInventoryFinishAsset(@RequestBody UpdateInventoryDraftRequest request){
        try {
            documentService.updateInventoryFinish(request);
            return ApiResponseDto.createdWithMessage("Update inventory finish success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/tool-find-all-increase")
    public ResponseEntity<?> findAllDocumentIncreaseTool(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentToolRequest findAllProcessAssetRequest) {
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDocumentToolIncrease(findAllProcessAssetRequest),
                    "Find all document tool increase!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-statistic-increase")
    public ResponseEntity<?> getStatisticIncreaseTool(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticToolIncrease(),
                    "Get statistic tool increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-be-assigned")
    public ResponseEntity<?> findAllProcessBeAssignedTool(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessBeAssignedRequest request){
        try {
            return ApiResponseDto.createdWithState(processService.findAllProcessBeAssignedResponse(request),
                    "Find all process be assigned success!", HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/tool-increase")
    public ResponseEntity<?> increaseTool(@RequestBody CreateIncreaseToolRequest request){
        try {
            processService.createIncreaseTool(request);
            return ApiResponseDto.createdWithMessage("Create increase tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-find-all-decrease")
    public ResponseEntity<?> findAllDocumentDecreaseTool(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentToolRequest findAllProcessAssetRequest) {
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDocumentToolDecrease(findAllProcessAssetRequest),
                    "Find all document tool decrease!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-statistic-decrease")
    public ResponseEntity<?> getStatisticDecreaseTool(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticToolDecrease(),
                    "Get statistic tool decrease success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/tool-details")
    public ResponseEntity<?> findToolDocumentDetailsByCodeDocumentAsset(@RequestParam("code") String code){
        try {
            return ApiResponseDto.createdWithState(documentService.findDetailsDocumentByCodeDocument(code),
                    "Find details document tool success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/tool-decrease")
    public ResponseEntity<?> decreaseTool(@RequestBody CreateDecreaseToolRequest request){
        try {
            processService.createDecreaseTool(request);
            return ApiResponseDto.createdWithMessage("Create increase tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/tool-document-inventory")
    public ResponseEntity<?> createDocumentInventoryTool(@RequestBody CreateInventoryToolRequest request){
        try {
            processService.createDocumentInventoryTool(request);
            return ApiResponseDto.createdWithMessage("Create inventory tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-find-all-document-inventory")
    public ResponseEntity<?> toolFindAllDocumentInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentToolRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDocumentToolDocumentInventory(findAllProcessAssetRequest),
                    "Find all document tool inventory!", HttpStatus.OK);
        }  catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-find-all-document-update-inventory")
    public ResponseEntity<?> toolFindAllDocumentUpdateInventory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllDocumentToolRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(documentService.findAllDocumentToolDocumentUpdateInventory(findAllProcessAssetRequest),
                    "Find all document tool update inventory!", HttpStatus.OK);
        }  catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-statistic-document-inventory")
    public ResponseEntity<?> getStatisticToolDocumentInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticToolDocumentInventory(),
                    "Get statistic inventory tool success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/tool-statistic-document-be-inventory")
    public ResponseEntity<?> getStatisticToolDocumentBeInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticToolDocumentBeInventory(),
                    "Get statistic be inventory tool success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/tool-statistic-update-inventory")
    public ResponseEntity<?> getStatisticToolUpdateInventoryAsset(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticToolUpdateInventory(),
                    "Get statistic update inventory tool success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
