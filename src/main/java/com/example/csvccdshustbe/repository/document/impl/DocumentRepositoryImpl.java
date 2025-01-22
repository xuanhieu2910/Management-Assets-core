package com.example.csvccdshustbe.repository.document.impl;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.dto.document.FindDetailsDocumentDto;
import com.example.csvccdshustbe.dto.document.tool.FindAllDocumentToolDto;
import com.example.csvccdshustbe.dto.process.*;
import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.document.DocumentRepositoryCustom;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.document.tool.FindAllDocumentToolRequest;
import com.example.csvccdshustbe.request.process.*;
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

public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Document>   findDocumentByCodeAndIdDepartment(String code, Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append("select doc.id_document, doc.code, doc.time_created,  " +
                "         doc.time_modified, doc.time_increase, doc.time_document,  " +
                "         doc.id_department, doc.id_department_original,  " +
                "         doc.status, doc.id_user_created, doc.id_user_modified  " +
                "from document doc  " +
                "where doc.code = :code  " +
                "and doc.id_department_original = :idDepartment ");
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
                document.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[7]));
                document.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                document.setIdUserCreated(ValueUtil.getIntegerByObject(obj[9]));
                document.setIdUserModified(ValueUtil.getIntegerByObject(obj[10]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Document> findDocumentByIdDepartment(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select doc.id_document, doc.code, doc.time_created, " +
                "         doc.time_modified, doc.time_increase, doc.time_document, " +
                "         doc.id_department, doc.id_department_original, " +
                "         doc.status, doc.id_user_created, doc.id_user_modified, " +
                "         doc.id_process, doc.description " +
                "from document doc " +
                " where doc.id_department_original = :idDepartment ORDER BY doc.id_document DESC ");
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
                document.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[7]));
                document.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                document.setIdUserCreated(ValueUtil.getIntegerByObject(obj[9]));
                document.setIdUserModified(ValueUtil.getIntegerByObject(obj[10]));
                document.setIdProcess(ValueUtil.getIntegerByObject(obj[11]));
                document.setDescription(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllDocumentAssetDto> findAllDocumentAssetDtoByIdsDepartment(FindAllDocumentAssetRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT document.code,type_process.code,type_process.name,user.full_name,process.status,      " +
                "         document.description,document.time_created, de.code codeDepartment, de.name nameDepartment,  " +
                "         document.status " +
                "FROM document       " +
                "    INNER JOIN process ON document.id_process = process.id_process    " +
                "    INNER JOIN type_process ON process.id_type_process = type_process.id_type_process    " +
                "    LEFT JOIN csvc_user user ON process.id_user_created = user.id_user          " +
                "    LEFT JOIN department de ON process.id_department = de.id_department    " +
                "    LEFT join asset_process on process.id_process = asset_process.id_process    " +
                "    INNER JOIN asset ON asset_process.id_asset = asset.id_asset      " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " );
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
                findAllDocumentAssetDto.setTimeCreated(ValueUtil.getLongByObject(obj[6]));
                findAllDocumentAssetDto.setCodeDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllDocumentAssetDto.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                findAllDocumentAssetDto.setStatusDocument(ValueUtil.getIntegerByObject(obj[9]));
                responses.add(findAllDocumentAssetDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllDocumentAsset(request));
    }

    @Override
    public Optional<FindDetailsDocumentDto> findDetailDocumentByCodeDocument(String codeDocument, List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append("select dc.id_document, dc.code,   " +
                "          cu.user_name, cu.full_name,   " +
                "         dc.time_created, dc.time_modified,   " +
                "         dc.time_increase, dc.time_document,   " +
                "         dc.id_department, dc.description,   " +
                "         st.id_state, st.status statusState,   " +
                "         ts.code codeTypeState, ts.id_type_state,   " +
                "         ts.name nameTypeState,   " +
                "         pr.id_process,pr.status, de.code codeDepartment,   " +
                "         de.name, dc.status documentStatus   " +
                "  from document dc       " +
                "      inner join department de on dc.id_department_original = de.id_department   " +
                "      inner join process pr on dc.id_process = pr.id_process   " +
                "      inner join csvc_user cu on pr.id_user_created = cu.id_user   " +
                "      inner join state st on pr.id_process = st.id_process   " +
                "      inner join type_state ts on st.id_type_state = ts.id_type_state   " +
                "      inner join  request on pr.id_process=request.id_process   " +
                "where dc.code = :codeDocument   " +
                "      and de.id_department in (:idsDepartment)   " +
                "      and cu.is_actived = :isActived ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeDocument", codeDocument);
        query.setParameter("idsDepartment", idsDepartment);
        query.setParameter("isActived", Constants.ACCOUNT_IS_UN_LOCK);
        List<Object[]> result = query.getResultList();
        List<BluePrintStateDto> dtosState = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
                Object[] objCommon = result.get(0);
                FindDetailsDocumentDto dto = new FindDetailsDocumentDto();
                setContructionCommonFindDetailsDocumentDto(dto, objCommon);
            for (Object[] obj : result){
                dtosState.add(setContructionBluePrintStateDto(obj));
            }
            dto.setBluePrintStateDto(dtosState);
            return Optional.of(dto);
        }
        return Optional.empty();
    }



    private BluePrintStateDto setContructionBluePrintStateDto(Object[] obj) {
        BluePrintStateDto bluePrintStateDto = new BluePrintStateDto();
        bluePrintStateDto.setIdState(ValueUtil.getIntegerByObject(obj[10]));
        bluePrintStateDto.setStatus(ValueUtil.getIntegerByObject(obj[11]));
        bluePrintStateDto.setCodeTypeState(ValueUtil.getStringByObject(obj[12]));
        bluePrintStateDto.setIdTypeState(ValueUtil.getIntegerByObject(obj[13]));
        bluePrintStateDto.setNameTypeState(ValueUtil.getStringByObject(obj[14]));
        bluePrintStateDto.setIdProcess(ValueUtil.getIntegerByObject(obj[15]));
        return bluePrintStateDto;
    }

    private void setContructionCommonFindDetailsDocumentDto(FindDetailsDocumentDto dto, Object[] obj) {
        dto.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
        dto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
        dto.setUserName(ValueUtil.getStringByObject(obj[2]));
        dto.setFullName(ValueUtil.getStringByObject(obj[3]));
        dto.setTimeCreated(ValueUtil.getLongByObject(obj[4]));
        dto.setTimeModified(ValueUtil.getLongByObject(obj[5]));
        dto.setTimeIncrease(ValueUtil.getStringByObject(obj[6]));
        dto.setTimeDocument(ValueUtil.getStringByObject(obj[7]));
        dto.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
        dto.setDescription(ValueUtil.getStringByObject(obj[9]));
        dto.setIdProcess(ValueUtil.getIntegerByObject(obj[15]));
        dto.setStatus(ValueUtil.getIntegerByObject(obj[16]));
        dto.setCodeDepartment(ValueUtil.getStringByObject(obj[17]));
        dto.setNameDepartment(ValueUtil.getStringByObject(obj[18]));
        dto.setStatusDocument(ValueUtil.getIntegerByObject(obj[19]));
    }

    private long countFindAllDocumentAsset(FindAllDocumentAssetRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) FROM document  " +
                        "                  INNER JOIN process ON document.id_process = process.id_process" +
                        "                  INNER JOIN type_process ON process.id_type_process = type_process.id_type_process" +
                        "                  LEFT JOIN csvc_user user ON process.id_user_created = user.id_user      " +
                        "                  LEFT JOIN department de ON process.id_department = de.id_department" +
                        "                  LEFT join asset_process on process.id_process = asset_process.id_process" +
                        "                  INNER JOIN asset ON asset_process.id_asset = asset.id_asset  " +
                " WHERE process.id_department IN (:idsDepartmentOriginal) ");
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
        if (ObjectUtils.isNotEmpty(request.getCodeAsset())){
            query.setParameter("codeAsset", request.getCodeAsset());
        }
        if (ObjectUtils.isNotEmpty(request.getSalt())){
            query.setParameter("salt", request.getSalt());
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
            sb.append(" and da.status = :statusTypeProcess ");
        }
        if (ObjectUtils.isNotEmpty(request.getCodeAsset())){
            sb.append(" and asset.code_asset = :codeAsset ");
        }
        if (ObjectUtils.isNotEmpty(request.getSalt())){
            sb.append(" and asset.salt = :salt ");
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

    @Override
    public Page<FindAllProcessAssetIncreaseDto>
    findAllProcessAssetIncreaseDtoByIdsDepartment(FindAllProcessAssetIncreaseRequest request, Pageable pageable){
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
    findAllProcessAssetDocumentInventoryDtoByIdsDepartment(FindAllProcessAssetDocumentInventoryRequest request, Pageable pageable) {
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
                FindAllProcessAssetInventoryDto findAllProcessAssetInventoryDto = new FindAllProcessAssetInventoryDto();
                findAllProcessAssetInventoryDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetInventoryDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetInventoryDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetInventoryDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetInventoryDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetInventoryDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetInventoryDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetInventoryDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetInventoryDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetInventoryDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetInventoryDto.setTimeInventory(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetInventoryDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetInventoryDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetInventoryDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetInventory(request));
    }

    private long countFindAllProcessAssetInventory(FindAllProcessAssetDocumentInventoryRequest request) {
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

    private void setParameterFindAllProcessAssetInventory(FindAllProcessAssetDocumentInventoryRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY);
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

    private void setConditionFindAllProcessAssetInventory(FindAllProcessAssetDocumentInventoryRequest request, StringBuilder sb) {
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


    @Override
    public Page<FindAllProcessAssetDecreaseDto>
    findAllProcessAssetDecreaseDtoByIdsDepartment(FindAllProcessAssetDecreaseRequest request, Pageable pageable) {
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
        setConditionFindAllProcessAssetDecrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetDecrease(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetDecreaseDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetDecreaseDto findAllProcessAssetDecreaseDto = new FindAllProcessAssetDecreaseDto();
                findAllProcessAssetDecreaseDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetDecreaseDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetDecreaseDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetDecreaseDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetDecreaseDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetDecreaseDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetDecreaseDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetDecreaseDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetDecreaseDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetDecreaseDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetDecreaseDto.setTimeDecrease(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetDecreaseDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetDecreaseDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetDecreaseDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetDecrease(request));
    }

    private void setConditionFindAllProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request, StringBuilder sb) {
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
        if (StringUtils.isNotBlank(request.getTimeDecrease())){
            sb.append(" and document.time_increase REGEXP :timeDecrease ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            if (request.getSortBy().equals("timeDocument")) {
                sb.append(" document.time_document ");
            }
            if (request.getSortBy().equals("timeDecrease")) {
                sb.append(" document.time_increase ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }

    private void setParameterFindAllProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DECREASE);
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
        if (StringUtils.isNotBlank(request.getTimeDecrease())){
            query.setParameter("timeDecrease", request.getTimeDecrease());
        }
    }
    private long countFindAllProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessAssetDecrease(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetDecrease(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public Page<FindAllProcessAssetChangeDto>
    findAllProcessAssetChangeDtoByIdsDepartment(FindAllProcessAssetChangeRequest request,
                                                                                            Pageable pageable) {
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
        setConditionFindAllProcessAssetChange(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetChange(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetChangeDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetChangeDto findAllProcessAssetChangeDto = new FindAllProcessAssetChangeDto();
                findAllProcessAssetChangeDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetChangeDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetChangeDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetChangeDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetChangeDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetChangeDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetChangeDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetChangeDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetChangeDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetChangeDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetChangeDto.setTimeChange(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetChangeDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetChangeDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetChangeDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetChange(request));
    }

    private void setConditionFindAllProcessAssetChange(FindAllProcessAssetChangeRequest request, StringBuilder sb) {
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
        if (StringUtils.isNotBlank(request.getTimeChange())){
            sb.append(" and document.time_increase REGEXP :timeChange ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            if (request.getSortBy().equals("timeDocument")) {
                sb.append(" document.time_document ");
            }
            if (request.getSortBy().equals("timeChange")) {
                sb.append(" document.time_increase ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }

    private void setParameterFindAllProcessAssetChange(FindAllProcessAssetChangeRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_CHANGE);
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
        if (StringUtils.isNotBlank(request.getTimeChange())){
            query.setParameter("timeChange", request.getTimeChange());
        }
    }
    private long countFindAllProcessAssetChange(FindAllProcessAssetChangeRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessAssetChange(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetChange(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public Page<FindAllProcessAssetRevaluationDto> findAllProcessAssetRevaluationDtoByIdsDepartment(FindAllProcessAssetRevaluationRequest request,
                                                                                            Pageable pageable) {
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
        setConditionFindAllProcessAssetRevaluation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetRevaluation(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetRevaluationDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetRevaluationDto findAllProcessAssetRevaluationDto = new FindAllProcessAssetRevaluationDto();
                findAllProcessAssetRevaluationDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetRevaluationDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetRevaluationDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetRevaluationDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetRevaluationDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetRevaluationDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetRevaluationDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetRevaluationDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetRevaluationDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetRevaluationDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetRevaluationDto.setTimeRevaluation(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetRevaluationDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetRevaluationDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetRevaluationDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetRevaluation(request));
    }

    @Override
    public Page<FindAllProcessAssetUpdateInventoryDto>
    findAllProcessAssetUpdateInventoryDtoByIdsDepartment(FindAllProcessAssetUpdateInventoryRequest request,
                                                         Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,     " +
                "        user.id_user, user.code_user, user.full_name,     " +
                "         de.id_department idDepartment, de.code codeDepartment,     " +
                "         de.name nameDepartment, document.time_created,     " +
                "         document.time_modified,document.time_increase,     " +
                "         document.time_document,process.status     " +
                "  FROM process      " +
                "           INNER JOIN document ON process.id_process = document.id_process     " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process     " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user      " +
                "           LEFT JOIN department de ON process.id_department = de.id_department     " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)     " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessUpdateInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetUpdateInventory(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessAssetUpdateInventoryDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllProcessAssetUpdateInventoryDto findAllProcessAssetRevaluationDto = new FindAllProcessAssetUpdateInventoryDto();
                findAllProcessAssetRevaluationDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                findAllProcessAssetRevaluationDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
                findAllProcessAssetRevaluationDto.setIdUserCreate(ValueUtil.getIntegerByObject(obj[2]));
                findAllProcessAssetRevaluationDto.setCodeUserCreate(ValueUtil.getStringByObject(obj[3]));
                findAllProcessAssetRevaluationDto.setNameUserCreate(ValueUtil.getStringByObject(obj[4]));
                findAllProcessAssetRevaluationDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                findAllProcessAssetRevaluationDto.setCodeDepartment(ValueUtil.getStringByObject(obj[6]));
                findAllProcessAssetRevaluationDto.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                findAllProcessAssetRevaluationDto.setTimeCreated(ValueUtil.getLongByObject(obj[8]));
                findAllProcessAssetRevaluationDto.setTimeModified(ValueUtil.getLongByObject(obj[9]));
                findAllProcessAssetRevaluationDto.setTimeInventory(ValueUtil.getStringByObject(obj[10]));
                findAllProcessAssetRevaluationDto.setTimeDocument(ValueUtil.getStringByObject(obj[11]));
                findAllProcessAssetRevaluationDto.setStatus(ValueUtil.getIntegerByObject(obj[12]));
                responses.add(findAllProcessAssetRevaluationDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessAssetUpdateInventory(request));
    }

    @Override
    public Optional<Document> findDocumentByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append("select dc.id_document, dc.id_process, dc.code,  " +
                "         dc.time_created, dc.time_modified,  " +
                "         dc.time_increase, dc.time_document,  " +
                "         dc.id_department_original,  " +
                "         dc.id_department, dc.description,  " +
                "         dc.status, dc.id_user_created,  " +
                "         dc.id_user_modified  " +
                "  from document dc       " +
                "  inner join process pr on dc.id_process = pr.id_process  " +
                "  where pr.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Document document = new Document();
                document.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
                document.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                document.setCode(ValueUtil.getStringByObject(obj[2]));
                document.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                document.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                document.setTimeIncrease(ValueUtil.getStringByObject(obj[5]));
                document.setTimeDocument(ValueUtil.getStringByObject(obj[6]));
                document.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[7]));
                document.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
                document.setDescription(ValueUtil.getStringByObject(obj[9]));
                document.setStatus(ValueUtil.getIntegerByObject(obj[10]));
                document.setIdUserCreated(ValueUtil.getIntegerByObject(obj[11]));
                document.setIdUserModified(ValueUtil.getIntegerByObject(obj[12]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Document> findDocumentByCodeDocumentAndStatus(String codeDocument, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_document, id_process, code,  " +
                "         time_created, time_modified, time_increase,  " +
                "         time_document, id_department_original,  " +
                "         id_department, description, status,  " +
                "         id_user_created, id_user_modified  " +
                "from document  " +
                "  where code = :codeDocument  " +
                "  and status = :statusDocument  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeDocument", codeDocument);
        query.setParameter("statusDocument", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Document document = new Document();
                document.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
                document.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                document.setCode(ValueUtil.getStringByObject(obj[2]));
                document.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                document.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                document.setTimeIncrease(ValueUtil.getStringByObject(obj[5]));
                document.setTimeDocument(ValueUtil.getStringByObject(obj[6]));
                document.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[7]));
                document.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
                document.setDescription(ValueUtil.getStringByObject(obj[9]));
                document.setStatus(ValueUtil.getIntegerByObject(obj[10]));
                document.setIdUserCreated(ValueUtil.getIntegerByObject(obj[11]));
                document.setIdUserModified(ValueUtil.getIntegerByObject(obj[12]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllDocumentToolDto>
    findAllDocumentToolIncreaseDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.full_name, user.user_name,  " +
                "       de.id_department idDepartment, de.code codeDepartment,  " +
                "       de.name nameDepartment, document.time_created,  " +
                "       document.time_modified,document.time_increase,  " +
                "       document.time_document,process.status, document.description  " +
                "  FROM process  " +
                "           INNER JOIN document ON process.id_process = document.id_process  " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "           LEFT JOIN department de ON process.id_department = de.id_department  " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllDocumentToolIncreaseDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolIncreaseDtoByIdsDepartment(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllDocumentToolDto> findAllDocumentToolDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                findAllDocumentToolDtos.add(writeDataFindAllDocumentToolDtos(obj));
            }
        }
        return new PageImpl<>(findAllDocumentToolDtos, pageable, countFindAllDocumentToolIncreaseDtoByIdsDepartment(request));
    }

    private long countFindAllDocumentToolIncreaseDtoByIdsDepartment(FindAllDocumentToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT count(0) count   " +
                "  FROM process   " +
                "           INNER JOIN document ON process.id_process = document.id_process   " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "           LEFT JOIN department de ON process.id_department = de.id_department   " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)   " +
                "  AND type_process.code = :codeTypeProcess ");
        setConditionFindAllDocumentToolIncreaseDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolIncreaseDtoByIdsDepartment(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
    @Override
    public Page<FindAllDocumentToolDto>
    findAllDocumentToolDecreaseDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.full_name, user.user_name,  " +
                "       de.id_department idDepartment, de.code codeDepartment,  " +
                "       de.name nameDepartment, document.time_created,  " +
                "       document.time_modified,document.time_increase,  " +
                "       document.time_document,process.status, document.description  " +
                "  FROM process  " +
                "           INNER JOIN document ON process.id_process = document.id_process  " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "           LEFT JOIN department de ON process.id_department = de.id_department  " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllDocumentToolDecreaseDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolDecreaseDtoByIdsDepartment(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllDocumentToolDto> findAllDocumentToolDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                findAllDocumentToolDtos.add(writeDataFindAllDocumentToolDtos(obj));
            }
        }
        return new PageImpl<>(findAllDocumentToolDtos, pageable, countFindAllDocumentToolDecreaseDtoByIdsDepartment(request));
    }

    @Override
    public Page<FindAllDocumentToolDto> findAllToolDocumentInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.full_name, user.user_name,  " +
                "       de.id_department idDepartment, de.code codeDepartment,  " +
                "       de.name nameDepartment, document.time_created,  " +
                "       document.time_modified,document.time_increase,  " +
                "       document.time_document,process.status, document.description  " +
                "  FROM process  " +
                "           INNER JOIN document ON process.id_process = document.id_process  " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "           LEFT JOIN department de ON process.id_department = de.id_department  " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllDocumentToolInventoryDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolInventoryDtoByIdsDepartment(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllDocumentToolDto> findAllDocumentToolDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                findAllDocumentToolDtos.add(writeDataFindAllDocumentToolDtos(obj));
            }
        }
        return new PageImpl<>(findAllDocumentToolDtos, pageable, countFindAllDocumentToolInventoryDtoByIdsDepartment(request));
    }

    @Override
    public Page<FindAllDocumentToolDto> findAllToolDocumentUpdateInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT process.id_process idProcess, document.code codeDocument,  " +
                "       user.full_name, user.user_name,  " +
                "       de.id_department idDepartment, de.code codeDepartment,  " +
                "       de.name nameDepartment, document.time_created,  " +
                "       document.time_modified,document.time_increase,  " +
                "       document.time_document,process.status, document.description  " +
                "  FROM process  " +
                "           INNER JOIN document ON process.id_process = document.id_process  " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "           LEFT JOIN department de ON process.id_department = de.id_department  " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllDocumentToolDto> findAllDocumentToolDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                findAllDocumentToolDtos.add(writeDataFindAllDocumentToolDtos(obj));
            }
        }
        return new PageImpl<>(findAllDocumentToolDtos, pageable, countFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(request));
    }

    private long countFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT count(0) count   " +
                "  FROM process   " +
                "           INNER JOIN document ON process.id_process = document.id_process   " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "           LEFT JOIN department de ON process.id_department = de.id_department   " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)   " +
                "  AND type_process.code = :codeTypeProcess ");
        setConditionFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(Query query, FindAllDocumentToolRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY_TOOL);
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllDocumentToolUpdateInventoryDtoByIdsDepartment(StringBuilder sb, FindAllDocumentToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            sb.append(" AND (document.code REGEXP :codeDocument ) ");
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            sb.append(" AND (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            sb.append(" AND document.time_document = :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            sb.append(" AND document.time_increase = :timeIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" AND de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" AND process.status = :status ");
        }
        sb.append("   ORDER BY document.id_document DESC ");
    }

    private long countFindAllDocumentToolInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT count(0) count   " +
                "  FROM process   " +
                "           INNER JOIN document ON process.id_process = document.id_process   " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "           LEFT JOIN department de ON process.id_department = de.id_department   " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)   " +
                "  AND type_process.code = :codeTypeProcess ");
        setConditionFindAllDocumentToolInventoryDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolInventoryDtoByIdsDepartment(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllDocumentToolInventoryDtoByIdsDepartment(Query query, FindAllDocumentToolRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY_TOOL);
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllDocumentToolInventoryDtoByIdsDepartment(StringBuilder sb, FindAllDocumentToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            sb.append(" AND (document.code REGEXP :codeDocument ) ");
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            sb.append(" AND (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            sb.append(" AND document.time_document = :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            sb.append(" AND document.time_increase = :timeIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" AND de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" AND process.status = :status ");
        }
        sb.append("   ORDER BY document.id_document DESC ");
    }

    private long countFindAllDocumentToolDecreaseDtoByIdsDepartment(FindAllDocumentToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT count(0) count   " +
                "  FROM process   " +
                "           INNER JOIN document ON process.id_process = document.id_process   " +
                "           INNER JOIN type_process ON process.id_type_process = type_process.id_type_process   " +
                "           LEFT JOIN csvc_user user ON process.id_user_created = user.id_user   " +
                "           LEFT JOIN department de ON process.id_department = de.id_department   " +
                "  WHERE process.id_department IN (:idsDepartmentOriginal)   " +
                "  AND type_process.code = :codeTypeProcess ");
        setConditionFindAllDocumentToolDecreaseDtoByIdsDepartment(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentToolDecreaseDtoByIdsDepartment(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private FindAllDocumentToolDto writeDataFindAllDocumentToolDtos(Object[] obj) {
        FindAllDocumentToolDto toolDto = new FindAllDocumentToolDto();
        toolDto.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
        toolDto.setCodeDocument(ValueUtil.getStringByObject(obj[1]));
        toolDto.setFullNameUser(ValueUtil.getStringByObject(obj[2]));
        toolDto.setNameUserCreate(ValueUtil.getStringByObject(obj[3]));
        toolDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[4]));
        toolDto.setCodeDepartment(ValueUtil.getStringByObject(obj[5]));
        toolDto.setNameDepartment(ValueUtil.getStringByObject(obj[6]));
        toolDto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
        toolDto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
        toolDto.setTimeIncrease(ValueUtil.getStringByObject(obj[9]));
        toolDto.setTimeDocument(ValueUtil.getStringByObject(obj[10]));
        toolDto.setStatus(ValueUtil.getIntegerByObject(obj[11]));
        toolDto.setDescription(ValueUtil.getStringByObject(obj[12]));
        return toolDto;
    }

    private void setParameterFindAllDocumentToolIncreaseDtoByIdsDepartment(Query query,
                                                                   FindAllDocumentToolRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE_TOOL);
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllDocumentToolIncreaseDtoByIdsDepartment(StringBuilder sb,
                                                                   FindAllDocumentToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            sb.append(" AND (document.code REGEXP :codeDocument ) ");
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            sb.append(" AND (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            sb.append(" AND document.time_document = :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            sb.append(" AND document.time_increase = :timeIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" AND de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" AND process.status = :status ");
        }
        sb.append("   ORDER BY document.id_document DESC ");
    }
    private void setConditionFindAllDocumentToolDecreaseDtoByIdsDepartment(StringBuilder sb,
                                                                           FindAllDocumentToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            sb.append(" AND (document.code REGEXP :codeDocument ) ");
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            sb.append(" AND (user.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            sb.append(" AND document.time_document = :timeDocument ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            sb.append(" AND document.time_increase = :timeIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" AND de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" AND process.status = :status ");
        }
        sb.append("   ORDER BY document.id_document DESC ");
    }
    private void setParameterFindAllDocumentToolDecreaseDtoByIdsDepartment(Query query,
                                                                           FindAllDocumentToolRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DECREASE_TOOL);
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }
    private void setConditionFindAllProcessAssetRevaluation(FindAllProcessAssetRevaluationRequest request, StringBuilder sb) {
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
        if (StringUtils.isNotBlank(request.getTimeRevaluation())){
            sb.append(" and document.time_increase REGEXP :timeRevaluation ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("timeCreate")) {
                sb.append(" document.time_created ");
            }
            if (request.getSortBy().equals("timeDocument")) {
                sb.append(" document.time_document ");
            }
            if (request.getSortBy().equals("timeRevaluation")) {
                sb.append(" document.time_increase ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY document.time_created desc ");
        }
    }

    private void setConditionFindAllProcessUpdateInventory(FindAllProcessAssetUpdateInventoryRequest request,
                                                           StringBuilder sb) {
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

    private void setParameterFindAllProcessAssetRevaluation(FindAllProcessAssetRevaluationRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_REVALUATION);
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
        if (StringUtils.isNotBlank(request.getTimeRevaluation())){
            query.setParameter("timeRevaluation", request.getTimeRevaluation());
        }
    }

    private void setParameterFindAllProcessAssetUpdateInventory(FindAllProcessAssetUpdateInventoryRequest request,
                                                                Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY);
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

    private long countFindAllProcessAssetRevaluation(FindAllProcessAssetRevaluationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessAssetRevaluation(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetRevaluation(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private long countFindAllProcessAssetUpdateInventory(FindAllProcessAssetUpdateInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0)  " +
                "FROM process  " +
                "         INNER JOIN document ON process.id_process = document.id_process  " +
                "         INNER JOIN type_process ON process.id_type_process = type_process.id_type_process  " +
                "         LEFT JOIN csvc_user user ON process.id_user_created = user.id_user  " +
                "         LEFT JOIN department de ON process.id_department = de.id_department  " +
                "WHERE process.id_department IN (:idsDepartmentOriginal)  " +
                "  AND type_process.code = :codeTypeProcess  ");
        setConditionFindAllProcessUpdateInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessAssetUpdateInventory(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

}
