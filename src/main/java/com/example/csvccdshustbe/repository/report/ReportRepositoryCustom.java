package com.example.csvccdshustbe.repository.report;

import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.dto.report.IncreaseDecreaseReport08bDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.request.report.CreateReportInCreaseAndDecreaseAllRequest;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReportRepositoryCustom {

    Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable);
    Page<FindAllReportDto> findAllReportDto(FindAllReportRequest request, Pageable pageable);
    Optional<Report> findReportByCodeAndStatus(String codeReport, Integer status);
    Optional<List<Object[]>> findInfoAssetForRevaluationReport(Integer idAssetProcess, Integer status);
    Optional<List<Object[]>> findInfoStakeHolderForRevaluationReport(Integer idAssetProcess);
    Optional<List<Object[]>> findInfoAssetForInventoryReport(String code);
    Optional<List<Object[]>> findInfoStakeHolderForInventoryReport(String code);

    Optional<CurrentUsageReport08aDto>  findAllCurrentUsageAssetGroundInReport(List<Integer> idsDepartment);

    Optional<CurrentUsageReport08aDto>  findAllCurrentUsageAssetHouseInReport(List<Integer> idsDepartment);

    Optional<CurrentUsageReport08aDto>  findAllCurrentUsageAssetCarInReport(List<Integer> idsDepartment);

    Optional<CurrentUsageReport08aDto>  findAllCurrentUsageAssetOtherInReport(List<Integer> idsDepartment);

    Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseGroundInReport(CreateReportInCreaseAndDecreaseAllRequest request);

    Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseHouseInReport(CreateReportInCreaseAndDecreaseAllRequest request);
    Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseCarInReport(CreateReportInCreaseAndDecreaseAllRequest request);
    Optional<IncreaseDecreaseReport08bDto>  findAllIncreaseDecreaseOtherAssetInReport(CreateReportInCreaseAndDecreaseAllRequest request);
}
