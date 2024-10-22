package com.example.csvccdshustbe.repository.process.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.repository.process.ProcessRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
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

public class ProcessRepositoryImpl implements ProcessRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllProcessAssetDto> findAllProcessAssetDtoByIdsDepartment(FindAllProcessAssetRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT process.id_process idProcess, document.code codeDocument, user.id_user, user.code_user, user.full_name,   " +
                "       de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,  " +
                "       document.time_created, document.time_modified,document.time_increase,document.time_document,process.status  " +
                "FROM process  " +
                "         LEFT JOIN data_process_asset da ON process.id_process = da.id_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "         LEFT JOIN document ON da.id_document = document.id_document  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  and da.status =: statusTypeProcess " );
        setConditionFindAllProcessAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetDto findAllProcessAssetDto = new FindAllProcessAssetDto();
                findAllProcessAssetDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetDto.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                findAllProcessAssetDto.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                findAllProcessAssetDto.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetDto.setTimeIncrease(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetDto.setTimeDocument(ValueUtil.getStringByObject(obj[12]));
                findAllProcessAssetDto.setStatus(ValueUtil.getIntegerByObject(obj[13]));
                responses.add(findAllProcessAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAsset(request));
    }

    private void setParameterFindAllProcessAsset(FindAllProcessAssetRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("statusTypeProcess", request.getStatusTypeProcess());

        if (StringUtils.isNotBlank(request.getCodeDocument())){
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (ObjectUtils.isNotEmpty(request.getTimeCreated())){
            query.setParameter("timeCreate", request.getTimeCreated());
        }
        if (ObjectUtils.isNotEmpty(request.getNameUserCreate())){
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (ObjectUtils.isNotEmpty(request.getTimeDocument())){
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (ObjectUtils.isNotEmpty(request.getTimeIncrease())){
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
    }

    private void setConditionFindAllProcessAsset(FindAllProcessAssetRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getCodeDocument())){
            sb.append(" and document.code = :codeDocument ");
        }
        if (ObjectUtils.isNotEmpty(request.getTimeCreated())){
            sb.append(" and document.time_created = :timeCreate ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and process.status = :status ");
        }
        if (ObjectUtils.isNotEmpty(request.getTimeCreated())){
            sb.append(" and document.time_document = :timeDocument ");
        }
        if (ObjectUtils.isNotEmpty(request.getTimeCreated())){
            sb.append(" and document.time_increase = :timeIncrease ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            if (request.getSortBy().equals("timeDocument")) {
                sb.append(" document.time_document ");
            }
            if (request.getSortBy().equals("timeIncrease")) {
                sb.append(" document.time_increase ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }

    private long countFindAllProcessAsset(FindAllProcessAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) FROM process  " +
                "         LEFT JOIN data_process_asset da ON process.id_process = da.id_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "         LEFT JOIN document ON da.id_document = document.id_document  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal); ");
        setConditionFindAllProcessAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAsset(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
}
