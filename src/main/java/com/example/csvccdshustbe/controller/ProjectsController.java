package com.example.csvccdshustbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Projects Controller", description = "The Projects APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/projects")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ProjectsController {
}
