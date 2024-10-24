package com.example.csvccdshustbe.repository.document.impl;

import com.example.csvccdshustbe.dto.document.DetailsDocumentBluePrintDto;
import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.document.DocumentRepositoryCustom;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
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

public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Document>   findDocumentByCodeAndIdDepartment(String code, Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append("select doc.id_document, doc.code, doc.time_created,    " +
                "        doc.time_modified, doc.time_increase, doc.time_document,  " +
                "        doc.id_department    " +
                " from document doc     " +
                " where doc.code = :code    " +
                " and doc.id_department = :idDepartment  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("code", code);
        query.setParameter("idDepartment", idDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Document document = new Document();
                document.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
                document.setCode(ValueUtil.getStringByObject(obj[1]));
                document.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                document.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                document.setTimeIncrease(ValueUtil.getStringByObject(obj[4]));
                document.setTimeDocument(ValueUtil.getStringByObject(obj[5]));
                document.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Document> findDocumentByIdDepartment(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append("select doc.id_document, doc.code, doc.time_created,    " +
                "        doc.time_modified, doc.time_increase, doc.time_document,  " +
                "        doc.id_department    " +
                " from document doc     " +
                " where doc.id_department = :idDepartment  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Document document = new Document();
                document.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
                document.setCode(ValueUtil.getStringByObject(obj[1]));
                document.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                document.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                document.setTimeIncrease(ValueUtil.getStringByObject(obj[4]));
                document.setTimeDocument(ValueUtil.getStringByObject(obj[5]));
                document.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllDocumentAssetDto> findAllDocumentAssetDtoByIdsDepartment(FindAllDocumentAssetRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT document.code,type_process.code,type_process.name,user.full_name,process.status,document.description,document.time_created, de.code codeDepartment, de.name nameDepartment " +
                " FROM document  " +
                "          LEFT JOIN data_process_asset da ON document.id_document = da.id_document   " +
                "          LEFT JOIN process ON da.id_process = process.id_process " +
                "          LEFT JOIN type_process ON process.id_type_process = type_process.id_type_process " +
                "          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user     " +
                "          LEFT JOIN department de ON process.id_department = de.id_department   " +
                "          LEFT JOIN asset ON da.id_asset = asset.id_asset   " +
                " WHERE process.id_department IN (:idsDepartmentOriginal)  " );
        setConditionFindAllDocumentAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllDocumentAssetDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllDocumentAssetDto findAllDocumentAssetDto = new FindAllDocumentAssetDto();
                findAllDocumentAssetDto.setCodeDocument(ValueUtil.getStringByObject(obj[0]));
                findAllDocumentAssetDto.setCodeTypeProcess(ValueUtil.getStringByObject(obj[1]));
                findAllDocumentAssetDto.setNameTypeProcess(ValueUtil.getStringByObject(obj[2]));
                findAllDocumentAssetDto.setNameUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllDocumentAssetDto.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                findAllDocumentAssetDto.setDescription(ValueUtil.getStringByObject(obj[5]));
                findAllDocumentAssetDto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                findAllDocumentAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllDocumentAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));


                responses.add(findAllDocumentAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllDocumentAsset(request));
    }

    @Override
    public Optional<DetailsDocumentBluePrintDto> findDetailDocumentByCodeDocument(String codeDocument) {
        return Optional.empty();
    }

    private long countFindAllDocumentAsset(FindAllDocumentAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) FROM document  " +
                "          LEFT JOIN data_process_asset da ON document.id_document = da.id_document   " +
                "          LEFT JOIN process ON da.id_process = process.id_process " +
                "          LEFT JOIN type_process ON process.id_type_process = type_process.id_type_process " +
                "          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user     " +
                "          LEFT JOIN department de ON process.id_department = de.id_department   " +
                "          LEFT JOIN asset ON da.id_asset = asset.id_asset   " +
                " WHERE process.id_department IN (:idsDepartmentOriginal); ");
        setConditionFindAllDocumentAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAsset(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllDocumentAsset(FindAllDocumentAssetRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());

        if (ObjectUtils.isNotEmpty(request.getStatusTypeProcess())){
            query.setParameter("statusTypeProcess", request.getStatusTypeProcess());
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
        if (ObjectUtils.isNotEmpty(request.getIdAsset())){
            query.setParameter("idAsset", request.getIdAsset());
        }

    }

    private void setConditionFindAllDocumentAsset(FindAllDocumentAssetRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_created = :timeCreate ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and process.status = :status ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusTypeProcess())){
            sb.append(" and da.status =: statusTypeProcess ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusTypeProcess())){
            sb.append(" and da.id_asset =: idAsset ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }
}
