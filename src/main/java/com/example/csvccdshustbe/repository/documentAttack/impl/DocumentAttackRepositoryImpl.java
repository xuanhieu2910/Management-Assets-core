package com.example.csvccdshustbe.repository.documentAttack.impl;

import com.example.csvccdshustbe.dto.documentAttack.FindAllDocumentAttackDto;
import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepositoryCustom;
import com.example.csvccdshustbe.request.documentAttack.FindAllDocumentAttackRequest;
import com.example.csvccdshustbe.request.documentAttack.FindAllDocumentAttackVisibleRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DocumentAttackRepositoryImpl implements DocumentAttackRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<DocumentAttack> findAllDocumentAttackVisibleResponse(
            FindAllDocumentAttackVisibleRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where 1=1 and document_attack.status = :status ");
        setConditionFindAllDocumentAttackVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAttackVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<DocumentAttack> documentAttacks = new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)) {
            for(Object[] obj :result) {
             DocumentAttack documentAttack=new DocumentAttack();
             documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
             documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
             documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
             documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
             documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
             documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
             documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
             documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
             documentAttacks.add(documentAttack);
            }
        }
        return new PageImpl<>(documentAttacks, pageable, countFindAllDocumentAttackActive(request));
    }

    @Override
    public Page<FindAllDocumentAttackDto>
    findAllDocumentAttackResponse(FindAllDocumentAttackRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select  document_attack.id_document_attack,   " +
                "        document_attack.name, document_attack.code, document_attack.id_department,   " +
                "        document_attack.date_determination_document, document_attack.status,   " +
                "        document_attack.time_created, document_attack.time_modified, " +
                "        de.name nameDepartment " +
                "from document_attack    " +
                "    left join department de on document_attack.id_department = de.id_department " +
                "where 1 = 1  ");
        setConditionFindAllDocumentAttack(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAttack(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllDocumentAttackDto> documentAttacks = new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)) {
            for(Object[] obj :result) {
                FindAllDocumentAttackDto documentAttack=new FindAllDocumentAttackDto();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                documentAttack.setNameDepartment(ValueUtil.getStringByObject(obj[8]));
                documentAttacks.add(documentAttack);
            }
        }
        return new PageImpl<>(documentAttacks, pageable, countFindAllDocumentAttack(request));
    }

    private long countFindAllDocumentAttackActive(FindAllDocumentAttackVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from document_attack " +
                "where 1 = 1 " +
                "  and document_attack.status = :status ");
        setConditionFindAllDocumentAttackVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAttackVisible(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllDocumentAttack(FindAllDocumentAttackRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("select  count(0) count " +
                "from document_attack " +
                "    left join department de on document_attack.id_department = de.id_department " +
                "where 1 = 1  ");
        setConditionFindAllDocumentAttack(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDocumentAttack(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private void setParameterFindAllDocumentAttackVisible(FindAllDocumentAttackVisibleRequest request, Query query) {
        query.setParameter("status", Constants.DOCUMENT_ATTACK_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setParameterFindAllDocumentAttack(FindAllDocumentAttackRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (Objects.isNull(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllDocumentAttackVisible(FindAllDocumentAttackVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (document_attack.name REGEXP :keyword ) ");
        }
    }

    private void setConditionFindAllDocumentAttack(FindAllDocumentAttackRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (document_attack.name REGEXP :keyword ) ");
        }
        if (!Objects.isNull(request.getStatus())){
            sb.append(" and document_attack.status = :status ");
        }
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where document_attack.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackById(Integer idDocumentAttack) {
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where document_attack.id_document_attack = :idDocumentAttack ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDocumentAttack", idDocumentAttack);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select document_attack.id_document_attack,   " +
                "       document_attack.name,   " +
                "       document_attack.code,   " +
                "       document_attack.id_department,   " +
                "       document_attack.date_determination_document,   " +
                "       document_attack.status,   " +
                "       document_attack.time_created,   " +
                "       document_attack.time_modified   " +
                "from document_attack   " +
                "where document_attack.id_document_attack = :idDocumentAttack   " +
                "and document_attack.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDocumentAttack", idDocumentAttack);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isExitsAssetByIdDocumentAttack(Integer idDocumentAttack) {
        StringBuilder sb = new StringBuilder();
        sb.append("select asset.id_asset " +
                "from document_attack doc " +
                "    inner join asset asset on doc.id_document_attack = asset.id_document_attack " +
                "where doc.id_document_attack = :idDocumentAttack ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDocumentAttack", idDocumentAttack);
        return !CollectionUtils.isEmpty(query.getResultList());
    }
}
