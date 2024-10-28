package com.example.csvccdshustbe.service.report.impl;

import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.repository.report.ReportRepository;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.response.report.FindAllReportResponse;
import com.example.csvccdshustbe.response.report.FindAllReportVisibleResponse;
import com.example.csvccdshustbe.service.report.ReportService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Override
    public Page<FindAllReportVisibleResponse> findAllReportVisible(FindAllReportVisibleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllReportDto> findAllReportDtos = reportRepository.findAllReportDtoVisible(request, pageable);
        return new PageImpl<>(convertToFindAllReportVisible(findAllReportDtos.stream().toList()), pageable, findAllReportDtos.getTotalElements());
    }

    private List<FindAllReportVisibleResponse> convertToFindAllReportVisible(List<FindAllReportDto> findAllDtos) {
        List<FindAllReportVisibleResponse> responses = new ArrayList<>();
        for (FindAllReportDto dto: findAllDtos){
            FindAllReportVisibleResponse response = new FindAllReportVisibleResponse();
            response.setIdReport(dto.getIdReport());
            response.setCodeReport(dto.getCodeReport());
            response.setTitleReport(dto.getTitleReport());
            response.setIdGovernmentCircular(dto.getIdGovernmentCircular());
            response.setTitleGovernmentCircular(dto.getTitleGovernmentCircular());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Page<FindAllReportResponse> findAllReport(FindAllReportRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllReportDto> findAllReportDtos = reportRepository.findAllReportDto(request, pageable);
        return new PageImpl<>(convertToFindAllReport(findAllReportDtos.stream().toList()), pageable, findAllReportDtos.getTotalElements());
    }

    @Override
    public String exportToPathFileReportByCodeReport(String codeReport) {
        Optional<Report> report = reportRepository.findReportByCodeAndStatus(codeReport.trim(), Constants.STATUS_REPORT_ACTIVE);
        if (report.isEmpty()){
            throw new NotFoundException("Don't exits report by code report!");
        }
        return report.get().getPath();
    }

    @Override
    public Report findReportByCode(String codeReport) {
        Optional<Report> report = reportRepository.findReportByCodeAndStatus(codeReport.trim(), Constants.STATUS_REPORT_ACTIVE);
        if (report.isEmpty()){
            throw new NotFoundException("Don't exits report by code report!");
        }
        return report.get();
    }

    private List<FindAllReportResponse> convertToFindAllReport(List<FindAllReportDto> findAllDtos) {
        List<FindAllReportResponse> responses = new ArrayList<>();
        for (FindAllReportDto dto: findAllDtos){
            FindAllReportResponse response = new FindAllReportResponse();
            response.setIdReport(dto.getIdReport());
            response.setCodeReport(dto.getCodeReport());
            response.setTitleReport(dto.getTitleReport());
            response.setIdGovernmentCircular(dto.getIdGovernmentCircular());
            response.setTitleGovernmentCircular(dto.getTitleGovernmentCircular());
            response.setPathReport(dto.getPath());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setStatus(dto.getStatus());
            responses.add(response);
        }
        return responses;
    }
}
