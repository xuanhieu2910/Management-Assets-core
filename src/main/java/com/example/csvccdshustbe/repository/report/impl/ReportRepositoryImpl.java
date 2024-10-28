package com.example.csvccdshustbe.repository.report.impl;

import com.example.csvccdshustbe.dto.report.FindAllReportDto;
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

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllReportDto> findAllReportDtoVisible(FindAllReportVisibleRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.id_report, rp.code, rp.title, rp.path,  " +
                "       rp.time_created, rp.time_modified, rp.id_user_created,  " +
                "       rp.id_user_modified, rp.status, rp.type_mime, gc.id_government_circular,  " +
                "       gc.title  " +
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
                "       gc.title  " +
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
                findAllReportDtos.add(allReportDto);
            }
        }
        return new PageImpl<>(findAllReportDtos, pageable, countFindAllReport(request));
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
