package com.example.csvccdshustbe.repository.report.impl;

import com.example.csvccdshustbe.dto.report.FindAllReportDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.Report;
import com.example.csvccdshustbe.repository.report.ReportRepositoryCustom;
import com.example.csvccdshustbe.request.report.FindAllReportRequest;
import com.example.csvccdshustbe.request.report.FindAllReportVisibleRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.id_report, rp.code, rp.title, rp.path,  " +
                "       rp.time_created, rp.time_modified, rp.id_user_created,  " +
                "       rp.id_user_modified, rp.status, rp.type_mime, gc.id_government_circular,  " +
                "       gc.title, rp.path_image  " +
                "from report rp  " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular  " +
                "where 1 = 1  " +
                "and rp.status = :statusRp and gc.status = :statusGc ");
        setFindAllReportVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        PageUtils.buildQuery(pageable, query);
        setParameterFindAllReportVisible(request, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReportDto> findAllReportDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllReportDto allReportDto = new FindAllReportDto();
                allReportDto.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                allReportDto.setCodeReport(ValueUtil.getStringByObject(obj[1]));
                allReportDto.setTitleReport(ValueUtil.getStringByObject(obj[2]));
                allReportDto.setPath(ValueUtil.getStringByObject(obj[3]));
                allReportDto.setTimeCreated(ValueUtil.getLongByObject(obj[4]));
                allReportDto.setTimeModified(ValueUtil.getLongByObject(obj[5]));
                allReportDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                allReportDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                allReportDto.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                allReportDto.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                allReportDto.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                allReportDto.setPathImage(ValueUtil.getStringByObject(obj[11]));
                findAllReportDtos.add(allReportDto);
            }
        }
        return new PageImpl<>(findAllReportDtos, pageable, countFindAllReportVisible(request));
    }

    @Override
    public Page<FindAllReportDto> findAllReportDto(FindAllReportRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.id_report, rp.code, rp.title, rp.path,  " +
                "       rp.time_created, rp.time_modified, rp.id_user_created,  " +
                "       rp.id_user_modified, rp.status, rp.type_mime, gc.id_government_circular,  " +
                "       gc.title, rp.path_image  " +
                "from report rp  " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular  " +
                "where 1 = 1  ");
        setFindAllReport(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        PageUtils.buildQuery(pageable, query);
        setParameterFindAllReport(request, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReportDto> findAllReportDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllReportDto allReportDto = new FindAllReportDto();
                allReportDto.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                allReportDto.setCodeReport(ValueUtil.getStringByObject(obj[1]));
                allReportDto.setTitleReport(ValueUtil.getStringByObject(obj[2]));
                allReportDto.setPath(ValueUtil.getStringByObject(obj[3]));
                allReportDto.setTimeCreated(ValueUtil.getLongByObject(obj[4]));
                allReportDto.setTimeModified(ValueUtil.getLongByObject(obj[5]));
                allReportDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                allReportDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                allReportDto.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                allReportDto.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                allReportDto.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                allReportDto.setPathImage(ValueUtil.getStringByObject(obj[11]));
                findAllReportDtos.add(allReportDto);
            }
        }
        return new PageImpl<>(findAllReportDtos, pageable, countFindAllReport(request));
    }

    @Override
    public Optional<Report> findReportByCodeAndStatus(String codeReport, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_report, code, title, path,  " +
                "       time_created, time_modified, id_user_created,  " +
                "       id_user_modified, status, type_mime,  " +
                "       id_government_circular, path_image  " +
                "from report  " +
                "where code = :codeReport  " +
                "and status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeReport", codeReport);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Report report = new Report();
                report.setIdReport(ValueUtil.getIntegerByObject(obj[0]));
                report.setCode(ValueUtil.getStringByObject(obj[1]));
                report.setTitle(ValueUtil.getStringByObject(obj[2]));
                report.setPath(ValueUtil.getStringByObject(obj[3]));
                report.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                report.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                report.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                report.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                report.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                report.setTypeMime(ValueUtil.getStringByObject(obj[9]));
                report.setIdGovernmentCircular(ValueUtil.getIntegerByObject(obj[10]));
                report.setPathImage(ValueUtil.getStringByObject(obj[11]));
                return Optional.of(report);
            }
        }
        return Optional.empty();
    }

    public Optional<List<Object[]>> findInfoAssetForInventoryReport(Integer idAssetProcess, Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT" +
                "    ap.id_asset_process AS id_asset_process,   -- 1.Số của report" +
                "    ap.time_modified AS time_modified,          -- 2.Thời gian kiểm kê" +
                "    a.name AS asset_name,                       -- 3.Tên tài sản" +
                "    a.code_asset AS asset_code,                 -- 4.Mã tài sản" +
                "    d.name AS department_name,                  -- 5.Nơi sử dụng (phòng ban)" +
                "    a.quantity AS quantity,                     -- 6.Số lượng" +
                "    ao.value AS original_value,                 -- 7.Nguyên giá" +
                "    ad.rest_value AS rest_value,                -- 8.Giá trị còn lại" +
                "    ap.value AS asset_process_value,            -- 9.Số lượng, nguyên giá, giá trị còn lại của kiểm kê" +
                "    a.notes AS notes                            -- 10.Ghi chú của tài sản" +
                "FROM" +
                "    asset AS a" +
                "        LEFT JOIN asset_original_of_formation AS ao ON a.id_asset = ao.id_asset" +
                "        LEFT JOIN asset_depreciation AS ad ON a.id_asset = ad.id_asset" +
                "        LEFT JOIN asset_process AS ap ON a.id_asset = ap.id_asset" +
                "        LEFT JOIN department AS d ON a.id_department = d.id_department" +
                "WHERE" +
                "        ap.status = :status AND ap.id_asset_process = :idAssetProcess"
        );

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetProcess", idAssetProcess);
        query.setParameter("status", status);

        List<Object[]> result = query.getResultList();

        if (!CollectionUtils.isEmpty(result)) {
            return Optional.of(result);
        }

        return Optional.empty();
    }

    public Optional<List<Object[]>> findInfoStakeHolderForInventoryReport(Integer idAssetProcess, Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT" +
                "    u.full_name AS user_name,  -- Tên người dùng" +
                "    rsh.position AS position,  -- Chức vụ của stakeholder" +
                "    d.name AS name_department    -- đại diện" +
                "FROM" +
                "    asset_process ap" +
                "        JOIN" +
                "    process p ON ap.id_process = p.id_process" +
                "        JOIN" +
                "    request r ON r.id_process = p.id_process" +
                "        JOIN" +
                "    request_stake_holder rsh ON r.id_request = rsh.id_request" +
                "        JOIN" +
                "    csvc_user u ON rsh.id_user = u.id_user" +
                "        JOIN" +
                "    user_role ur ON u.id_user = ur.id_user" +
                "        JOIN" +
                "    department d ON ur.id_department = d.id_department" +
                "WHERE" +
                "        ap.id_asset_process = :idAssetProcess" +
                "  AND ap.status = :status"
        );

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetProcess", idAssetProcess);
        query.setParameter("status", status);

        List<Object[]> result = query.getResultList();

        if (!CollectionUtils.isEmpty(result)) {
            return Optional.of(result);
        }

        return Optional.empty();
    }


    private void setParameterFindAllReport(FindAllReportRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            query.setParameter("codeReport", request.getCodeReport());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            query.setParameter("idGovernmentCircular", request.getIdGovernmentCircular());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusReport())) {
            query.setParameter("statusRp", request.getIdGovernmentCircular());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusGovernmentCircular())) {
            query.setParameter("statusGc", request.getIdGovernmentCircular());
        }
    }

    private void setFindAllReport(FindAllReportRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            sb.append(" and  (rp.code REGEXP :codeReport ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (rp.title REGEXP :keyword) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            sb.append(" and gc.id_government_circular = :idGovernmentCircular ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusReport())) {
            sb.append(" and rp.status = :statusRp ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusGovernmentCircular())) {
            sb.append(" and gc.status = :statusGc ");
        }
    }

    private long countFindAllReportVisible(FindAllReportVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from report rp " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular " +
                "where 1 = 1 " +
                "and rp.status = :statusRp and gc.status = :statusGc ");
        setFindAllReportVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllReportVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllReport(FindAllReportRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from report rp " +
                "    inner join government_circular gc on rp.id_government_circular = gc.id_government_circular " +
                "where 1 = 1 ");
        setFindAllReport(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllReport(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllReportVisible(FindAllReportVisibleRequest request, Query query) {
        query.setParameter("statusRp", Constants.STATUS_REPORT_ACTIVE);
        query.setParameter("statusGc", Constants.STATUS_GOVERNMENT_ACTIVE);
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            query.setParameter("codeReport", request.getCodeReport());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            query.setParameter("idGovernmentCircular", request.getIdGovernmentCircular());
        }
    }

    private void setFindAllReportVisible(FindAllReportVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeReport())) {
            sb.append(" and  (rp.code REGEXP :codeReport ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (rp.title REGEXP :keyword) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdGovernmentCircular())) {
            sb.append(" and gc.id_government_circular = :idGovernmentCircular ");
        }
    }


}
