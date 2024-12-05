package com.example.csvccdshustbe.service.report.impl;

import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.dto.report.IncreaseDecreaseReport08bDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepository;
import com.example.csvccdshustbe.repository.report.ReportRepository;
import com.example.csvccdshustbe.request.report.CreateReportInCreaseAndDecreaseAllRequest;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.response.report.FindAllReportResponse;
import com.example.csvccdshustbe.response.report.FindAllReportVisibleResponse;
import com.example.csvccdshustbe.service.report.ReportService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
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
    @Autowired
    FilesStorageService filesStorageService;

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
        writeDataToMapReport08a(data, rowNum++, reportRepository.findAllCurrentUsageAssetGroundInReport(csvcUser.getIdsDepartmentCurrent()));
        writeDataToMapReport08a(data, rowNum++, reportRepository.findAllCurrentUsageAssetHouseInReport(csvcUser.getIdsDepartmentCurrent()));
        writeListDataToMapReport08a(data,rowNum++,reportRepository.findAllCurrentUsageAssetShapeInReport(csvcUser.getIdsDepartmentCurrent()));
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

        private void writeDataToMapReport08a(
            Map<Integer, Object[]> data,
            int rowNum,
            Optional<CurrentUsageReport08aDto> recordOptional) {
        if (recordOptional.isPresent()) {
            CurrentUsageReport08aDto record = recordOptional.get();
            data.put(rowNum, new Object[]{
                    record.getNameCategory(),
                    record.getCountAsset(),
                    record.getAcreage(),
                    record.getTotalStateManagement(),
                    record.getTotalNoBusiness(),
                    record.getTotalBusiness(),
                    record.getTotalRent(),
                    record.getTotalBonds(),
                    record.getTotalSynthetic(),
                    record.getTotalOther(),
            });
        }
    }


    private void writeListDataToMapReport08a(
            Map<Integer, Object[]> data,
            int rowNum,
            List<CurrentUsageReport08aDto> recordOptional) {
        for (CurrentUsageReport08aDto record : recordOptional) {
                data.put(rowNum, new Object[]{
                        record.getNameCategory(),
                        record.getCountAsset(),
                        record.getAcreage(),
                        record.getTotalStateManagement(),
                        record.getTotalNoBusiness(),
                        record.getTotalBusiness(),
                        record.getTotalRent(),
                        record.getTotalBonds(),
                        record.getTotalSynthetic(),
                        record.getTotalOther(),
                });
            rowNum++;
            }
    }


    public String ReportIncreaseDecreaseAsset08b(CreateReportInCreaseAndDecreaseAllRequest request) throws IOException{
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fileExcel = "D:\\CompanyBk\\CSVC\\Sample_Increase_Decrease_Report.xlsx";

        FileInputStream file = new FileInputStream(new File(fileExcel));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Map<Integer, Object[]> data = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum() + 1;
        setIdsDepartmentOriginal(request);
        writeDataToMapReport08b(data, rowNum++, reportRepository.findAllIncreaseDecreaseGroundInReport(request));
        writeDataToMapReport08b(data, rowNum++, reportRepository.findAllIncreaseDecreaseHouseInReport(request));
        writeListDataToMapReport08b(data,rowNum++, reportRepository.findAllIncreaseDecreaseAssetShapeInReport(request));
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

    @Override
    public String downloadFileInventoryReport(String code) throws IOException {
        return filesStorageService.downLoadInventoryReport(code.trim());
    }

    @Override
    public String downloadFileRevaluationReport(Integer idAssetProcess) throws IOException {
        return filesStorageService.downLoadRevaluationReport(idAssetProcess, Constants.STATUS_ASSET_PROCESS_ACTIVE);
    }
    private void setIdsDepartmentOriginal(CreateReportInCreaseAndDecreaseAllRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }


    private void writeDataToMapReport08b(
            Map<Integer, Object[]> data,
            int rowNum,
            Optional<IncreaseDecreaseReport08bDto> recordOptional) {
        if (recordOptional.isPresent()) {
            IncreaseDecreaseReport08bDto record = recordOptional.get();
            data.put(rowNum, new Object[]{
                    record.getNameCategory(),
                    record.getCountAssetStart(),
                    record.getAcreageStart(),
                    record.getTotalOriginalStart(),
                    record.getCountAssetIncrease(),
                    record.getAcreageIncrease(),
                    record.getTotalOriginalIncrease(),
                    record.getCountDecrease(),
                    record.getAcreageDecrease(),
                    record.getTotalOriginalDecrease(),
                    record.getCountAssetEnd(),
                    record.getAcreageEnd(),
                    record.getTotalOriginalEnd(),
            });
        }
    }

    private void writeListDataToMapReport08b(
            Map<Integer, Object[]> data,
            int rowNum,
            List<IncreaseDecreaseReport08bDto> recordOptional) {
        for (IncreaseDecreaseReport08bDto record : recordOptional) {
            data.put(rowNum, new Object[]{
                    record.getNameCategory(),
                    record.getCountAssetStart(),
                    record.getAcreageStart(),
                    record.getTotalOriginalStart(),
                    record.getCountAssetIncrease(),
                    record.getAcreageIncrease(),
                    record.getTotalOriginalIncrease(),
                    record.getCountDecrease(),
                    record.getAcreageDecrease(),
                    record.getTotalOriginalDecrease(),
                    record.getCountAssetEnd(),
                    record.getAcreageEnd(),
                    record.getTotalOriginalEnd(),
            });
            rowNum++;
        }
    }
}
