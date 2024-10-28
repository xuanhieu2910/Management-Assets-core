package com.example.csvccdshustbe.repository.report;

import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {

    Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable);
    Page<FindAllReportDto> findAllReportDto(FindAllReportRequest request, Pageable pageable);

}
