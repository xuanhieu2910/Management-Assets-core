package com.example.csvccdshustbe.service.report.impl;

import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepository;
import com.example.csvccdshustbe.repository.report.ReportRepository;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.response.report.FindAllReportResponse;
import com.example.csvccdshustbe.response.report.FindAllReportVisibleResponse;
import com.example.csvccdshustbe.service.report.ReportService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    AssetCurrentUsageRepository assetCurrentUsageRepository;
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
        return PropertiesUtil.getProperty("hust.csvc.static.location.static.files") + report.get().getPath();
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
    public String ReportUsingAsset08a() throws IOException{
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fileExcel = "D:\\CompanyBk\\CSVC\\Sample_Current_Usage_Report.xlsx";

        FileInputStream file = new FileInputStream(new File(fileExcel));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Map<Integer, Object[]> data = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum() + 1;
        CurrentUsageReport08aDto recordsGroundToWrite = assetCurrentUsageRepository.findAllCurrentUsageAssetGroundInReport(csvcUser.getIdsDepartmentCurrent());
            data.put(rowNum, new Object[]{
                    recordsGroundToWrite.getCountAsset(),
                    recordsGroundToWrite.getAcreage(),
                    recordsGroundToWrite.getTotalStateManagement(),
                    recordsGroundToWrite.getTotalNoBusiness(),
                    recordsGroundToWrite.getTotalBusiness(),
                    recordsGroundToWrite.getTotalRent(),
                    recordsGroundToWrite.getTotalBonds(),
                    recordsGroundToWrite.getTotalSynthetic(),
                    recordsGroundToWrite.getTotalOther(),
            });
        rowNum++;
        CurrentUsageReport08aDto recordsHouseToWrite = assetCurrentUsageRepository.findAllCurrentUsageAssetHouseInReport(csvcUser.getIdsDepartmentCurrent());
        data.put(rowNum, new Object[]{
                recordsHouseToWrite.getCountAsset(),
                recordsHouseToWrite.getAcreage(),
                recordsHouseToWrite.getTotalStateManagement(),
                recordsHouseToWrite.getTotalNoBusiness(),
                recordsHouseToWrite.getTotalBusiness(),
                recordsHouseToWrite.getTotalRent(),
                recordsHouseToWrite.getTotalBonds(),
                recordsHouseToWrite.getTotalSynthetic(),
                recordsHouseToWrite.getTotalOther(),
        });
        rowNum++;
        CurrentUsageReport08aDto recordsCarToWrite = assetCurrentUsageRepository.findAllCurrentUsageAssetCarInReport(csvcUser.getIdsDepartmentCurrent());
        data.put(rowNum, new Object[]{
                recordsCarToWrite.getCountAsset(),
                recordsCarToWrite.getAcreage(),
                recordsCarToWrite.getTotalStateManagement(),
                recordsCarToWrite.getTotalNoBusiness(),
                recordsCarToWrite.getTotalBusiness(),
                recordsCarToWrite.getTotalRent(),
                recordsCarToWrite.getTotalBonds(),
                recordsCarToWrite.getTotalSynthetic(),
                recordsCarToWrite.getTotalOther(),
        });
        rowNum++;
        CurrentUsageReport08aDto recordsOtherToWrite = assetCurrentUsageRepository.findAllCurrentUsageAssetOtherInReport(csvcUser.getIdsDepartmentCurrent());
        data.put(rowNum, new Object[]{
                recordsOtherToWrite.getCountAsset(),
                recordsOtherToWrite.getAcreage(),
                recordsOtherToWrite.getTotalStateManagement(),
                recordsOtherToWrite.getTotalNoBusiness(),
                recordsOtherToWrite.getTotalBusiness(),
                recordsOtherToWrite.getTotalRent(),
                recordsOtherToWrite.getTotalBonds(),
                recordsOtherToWrite.getTotalSynthetic(),
                recordsOtherToWrite.getTotalOther(),
        });

        Set<Integer> keySet = data.keySet();
        for (Integer key : keySet){
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = data.get(key);
            int cellNum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(fileExcel);
            workbook.write(out);
            out.close();
            return fileExcel;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
