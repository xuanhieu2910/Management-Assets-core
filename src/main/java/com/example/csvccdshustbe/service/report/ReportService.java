package com.example.csvccdshustbe.service.report;

import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.request.report.CreateReportInCreaseAndDecreaseAllRequest;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.response.report.FindAllReportResponse;
import com.example.csvccdshustbe.response.report.FindAllReportVisibleResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ReportService {

    Page<FindAllReportVisibleResponse> findAllReportVisible(FindAllReportVisibleRequest request);
    Page<FindAllReportResponse> findAllReport(FindAllReportRequest request);

    String exportToPathFileReportByCodeReport(String codeReport);
    Report findReportByCode(String codeReport);

    String ReportUsingAsset08a() throws IOException;

    String downloadFileInventoryReportByCodeDocument(FindAllAssetProcessRequest request) throws IOException;

    String downloadFileRevaluationReport(Integer idAssetProcess) throws IOException;

    String ReportIncreaseDecreaseAsset08b(CreateReportInCreaseAndDecreaseAllRequest request) throws IOException;

}
