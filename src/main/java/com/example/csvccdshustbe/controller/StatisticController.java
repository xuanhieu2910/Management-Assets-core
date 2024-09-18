package com.example.csvccdshustbe.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistic Controller", description = "The Statistic APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class StatisticController {




}
