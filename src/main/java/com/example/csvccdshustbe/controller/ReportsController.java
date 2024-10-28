package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reports Controller", description = "The Reports APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllReportVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllReportVisibleRequest request){
        try {
            return null;
        } catch (Exception e){
            return null;
        }
    }
}
