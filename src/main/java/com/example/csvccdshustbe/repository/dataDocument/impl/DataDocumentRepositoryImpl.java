package com.example.csvccdshustbe.repository.dataDocument.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.repository.dataDocument.DataDocumentRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
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

public class DataDocumentRepositoryImpl implements DataDocumentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<DataDocument> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select dp.id_data_document, dp.id_document, dp.id_asset,  " +
                "       dp.time_created, dp.time_modified, dp.status  " +
                "  from data_document dp  " +
                "  inner join asset asset on dp.id_asset = asset.id_asset  " +
                "  inner join document dc on dp.id_document = dc.id_document  " +
                "  inner join process pro on dc.id_process = pro.id_process  " +
                "  inner join type_process tp on pro.id_type_process = tp.code    " +
                "  where asset.id_asset in (:idAsset)    " +
                "  and tp.code = :codeTypeProcess  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idsAsset);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE);
        List<Object[]> result = query.getResultList();
        List<DataDocument> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                DataDocument processAsset = new DataDocument();
                processAsset.setIdDataDocument(ValueUtil.getIntegerByObject(obj[0]));
                processAsset.setIdDocument(ValueUtil.getIntegerByObject(obj[1]));
                processAsset.setIdAsset(ValueUtil.getIntegerByObject(obj[2]));
                processAsset.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                processAsset.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                processAsset.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                responses.add(processAsset);
            }
        }
        return responses;
    }


    @Override
    public Page<FindAllProcessAssetDto> findAllProcessAssetDtoByIdsDepartment(FindAllProcessAssetRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT process.id_process idProcess, document.code codeDocument, user.id_user, user.code_user, user.full_name,     " +
                "                        de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,    " +
                "                        document.time_created, document.time_modified,document.time_increase,document.time_document,process.status    " +
                "                 FROM process " +
                "                          LEFT JOIN document ON process.id_process = document.id_process " +
                "                          LEFT JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "                          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user    " +
                "                          LEFT JOIN department de ON process.id_department = de.id_department    " +
                "                 WHERE process.id_department IN (:idsDepartmentOriginal)  " );
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
                findAllProcessAssetDto.setTimeIncrease(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAsset(request));
    }

    private void setParameterFindAllProcessAsset(FindAllProcessAssetRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());

        if (ObjectUtils.isNotEmpty(request.getCodeTypeProcess())){
            query.setParameter("codeTypeProcess", request.getCodeTypeProcess());
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            query.setParameter("timeCreate", request.getTimeCreated());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())){
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())){
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
    }

    private void setConditionFindAllProcessAsset(FindAllProcessAssetRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            sb.append(" and document.code = :codeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_created = :timeCreate ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and process.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_document = :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_increase = :timeIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getCodeTypeProcess())){
            sb.append(" and type_process.code = :codeTypeProcess ");
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
                "                          LEFT JOIN document ON process.id_process = document.id_process " +
                "                          LEFT JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "                          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user    " +
                "                          LEFT JOIN department de ON process.id_department = de.id_department    " +
                "WHERE process.id_department IN (:idsDepartmentOriginal) ");
        setConditionFindAllProcessAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAsset(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
}
