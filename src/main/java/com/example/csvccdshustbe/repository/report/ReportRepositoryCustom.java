package com.example.csvccdshustbe.repository.report;

import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportRepositoryCustom {

    Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable);
    Page<FindAllReportDto> findAllReportDto(FindAllReportRequest request, Pageable pageable);
    Optional<Report> findReportByCodeAndStatus(String codeReport, Integer status);

}
