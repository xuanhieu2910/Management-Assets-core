package com.example.csvccdshustbe.repository.dataDocument.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetIncreaseDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetInventoryDto;
import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.repository.dataDocument.DataDocumentRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
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
    public Page<FindAllProcessAssetIncreaseDto>
    findAllProcessAssetIncreaseDtoByIdsDepartment(FindAllProcessAssetIncreaseRequest request,Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.id_user, user.code_user, user.full_name,  " +
                "        de.id_department idDepartment, de.code codeDepartment,  " +
                "        de.name nameDepartment, document.time_created,  " +
                "        document.time_modified,document.time_increase,  " +
                "        document.time_document,process.status  " +
                " FROM process   " +
                "          INNER JOIN document ON process.id_process = document.id_process  " +
                "          INNER JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "          LEFT JOIN department de ON process.id_department = de.id_department   " +
                " WHERE process.id_department IN (:idsDepartmentOriginal)    " +
                " AND type_process.code = :codeTypeProcess " );
        setConditionFindAllProcessAssetIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetIncrease(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetIncreaseDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetIncreaseDto findAllProcessAssetIncreaseDto = new FindAllProcessAssetIncreaseDto();
                findAllProcessAssetIncreaseDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetIncreaseDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetIncreaseDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetIncreaseDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetIncreaseDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetIncreaseDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetIncreaseDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetIncreaseDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetIncreaseDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetIncreaseDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetIncreaseDto.setTimeIncrease(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetIncreaseDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetIncreaseDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetIncreaseDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetIncrease(request));
    }

    @Override
    public Page<FindAllProcessAssetInventoryDto>
    findAllProcessAssetInventoryDtoByIdsDepartment(FindAllProcessAssetInventoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.id_user, user.code_user, user.full_name,  " +
                "        de.id_department idDepartment, de.code codeDepartment,  " +
                "        de.name nameDepartment, document.time_created,  " +
                "        document.time_modified,document.time_increase,  " +
                "        document.time_document,process.status  " +
                " FROM process   " +
                "          INNER JOIN document ON process.id_process = document.id_process  " +
                "          INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "          LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "          LEFT JOIN department de ON process.id_department = de.id_department  " +
                " WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                " AND type_process.code = :codeTypeProcess ");
        setConditionFindAllProcessAssetInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetInventory(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetInventoryDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetInventoryDto findAllProcessAssetIncreaseDto = new FindAllProcessAssetInventoryDto();
                findAllProcessAssetIncreaseDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetIncreaseDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetIncreaseDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetIncreaseDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetIncreaseDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetIncreaseDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetIncreaseDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetIncreaseDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetIncreaseDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetIncreaseDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetIncreaseDto.setTimeInventory(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetIncreaseDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetIncreaseDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetIncreaseDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetInventory(request));
    }

    private long countFindAllProcessAssetInventory(FindAllProcessAssetInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessAssetInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetInventory(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE);
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            query.setParameter("timeCreate", request.getTimeCreated());
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())) {
            query.setParameter("nameDepartment", request.getNameDepartment());
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

    private void setParameterFindAllProcessAssetInventory(FindAllProcessAssetInventoryRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INVENTORY);
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            query.setParameter("timeCreate", request.getTimeCreated());
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())) {
            query.setParameter("nameDepartment", request.getNameDepartment());
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
        if (StringUtils.isNotBlank(request.getTimeInventory())){
            query.setParameter("timeInventory", request.getTimeInventory());
        }
    }

    private void setConditionFindAllProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            sb.append(" and document.code = :codeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_created = :timeCreate ");
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())){
            sb.append(" and de.name = :nameDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and process.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and process.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())){
            sb.append(" and document.time_document REGEXP :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())){
            sb.append(" and document.time_increase REGEXP :timeIncrease ");
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

    private void setConditionFindAllProcessAssetInventory(FindAllProcessAssetInventoryRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())){
            sb.append(" and document.code = :codeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and document.time_created = :timeCreate ");
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())){
            sb.append(" and de.name = :nameDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and process.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and process.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())){
            sb.append(" and document.time_document REGEXP :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeInventory())){
            sb.append(" and document.time_increase REGEXP :timeInventory ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            if (request.getSortBy().equals("timeDocument")) {
                sb.append(" document.time_document ");
            }
            if (request.getSortBy().equals("timeInventory")) {
                sb.append(" document.time_increase ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }

    private long countFindAllProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessAssetIncrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetIncrease(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
}
