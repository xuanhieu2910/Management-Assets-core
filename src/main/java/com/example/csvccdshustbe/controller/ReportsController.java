package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.request.report.CreateReportInCreaseAndDecreaseAllRequest;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.service.report.ReportService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Reports Controller", description = "The Reports APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {

    @Autowired
    ReportService reportService;



    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllReportVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllReportVisibleRequest request){
        try {
            return ApiResponseDto.createdWithState(reportService.findAllReportVisible(request),
                    "Find all report visible success", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllReport(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllReportRequest request){
        try {
            return ApiResponseDto.createdWithState(reportService.findAllReport(request),
                    "Find all report visible success", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadReportByCode(@RequestParam("code") String code){
        try {
            String pathFile = reportService.exportToPathFileReportByCodeReport(code);
            return ApiResponseDto.createdWithState(pathFile, "Download file code success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/preview")
    public ResponseEntity<?> previewReportByCode(@RequestParam("code") String code){
        try {
            String pathImage = PropertiesUtil.getProperty("hust.csvc.static.location.static.files")  +
                    reportService.findReportByCode(code).getPathImage();
            return ApiResponseDto.createdWithState(pathImage, "Preview report details success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/download-report-current-usage")
    public ResponseEntity<?> downloadReportCurrentUsageSuccess(){
        try {
            return ApiResponseDto.createdWithState(reportService.ReportUsingAsset08a(),
                    "Download report success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-report-inventory")
    public ResponseEntity<?> downloadReportInventory(FindAllAssetProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(reportService.downloadFileInventoryReportByCodeDocument(request),
                    "Download inventory report success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-report-revaluation")
    public ResponseEntity<?> downloadReportRevaluation(@RequestParam("idAssetProcess") Integer idAssetProcess){
        try {
            return ApiResponseDto.createdWithState(reportService.downloadFileRevaluationReport(idAssetProcess),
                    "Download inventory report success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-report-increase-decrease-all-asset")
    public ResponseEntity<?> downloadReportIncreaseDecreaseSuccess(CreateReportInCreaseAndDecreaseAllRequest request){
        try {
            return ApiResponseDto.createdWithState(reportService.ReportIncreaseDecreaseAsset08b(request),
                    "Download report success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
