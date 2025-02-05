package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.service.originalOfFormationTool.OriginalOfFormationToolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Original of formation tool Controller", description = "The Original of formation tool APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/original-of-formation-tool")
public class OriginalOfFormationToolController {

    @Autowired
    OriginalOfFormationToolService originalOfFormationToolService;



}
