package com.example.csvccdshustbe.repository.reason.impl;

import com.example.csvccdshustbe.dto.reason.FindAllReasonDto;
import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.repository.reason.ReasonRepositoryCustom;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.request.reason.FindAllTypeActionReasonsRequest;
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
import java.util.stream.Collectors;

public class ReasonRepositoryImpl implements ReasonRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Reason> findReasonByIdReasonAndStatus(Integer idReason, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select re.id_reason, re.name, re.type_reason,   " +
                "       re.time_created, re.time_modified, re.status   " +
                "from reason re   " +
                "where re.id_reason = :idReason and re.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idReason", idReason);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Reason reason = new Reason();
                reason.setIdReason(ValueUtil.getIntegerByObject(obj[0]));
                reason.setName(ValueUtil.getStringByObject(obj[1]));
                reason.setTypeReason(ValueUtil.getIntegerByObject(obj[2]));
                reason.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                reason.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                reason.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(reason);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllReasonDto> findReasonsByTypeActionAndStatus(FindAllTypeActionReasonsRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT re.id_reason, re.name, re.type_reason, " +
                "        re.time_created, re.time_modified, re.status, re.type_action " +
                " FROM reason re " +
                " WHERE re.type_action = :typeAction AND re.status = :status ");

        setConditionFindAllTypeActionReason(request, sb);

        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeActionReason(request, query);

        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReasonDto> findAllReasonDtos = new ArrayList<>();

        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllReasonDto dto = new FindAllReasonDto();
                dto.setIdReason(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setTypeReason(ValueUtil.getIntegerByObject(obj[2]));
                dto.setTimeCreated(ValueUtil.getLongByObject(obj[3]));
                dto.setTimeModified(ValueUtil.getLongByObject(obj[4]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                dto.setTypeAction(ValueUtil.getStringByObject(obj[6]));
                findAllReasonDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllReasonDtos, pageable, countFindAllTypeActionReason(request));
    }


    @Override
    public Page<FindAllReasonDto> findAllReasonResponse(FindAllReasonsRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select re.id_reason, re.name, re.type_reason,   " +
                "       re.time_created, re.time_modified, re.status   " +
                "from reason re   " +
                "where 1 = 1  ");
        setConditionFindAllReason(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());

        setParameterFindAllReason(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllReasonDto> findAllReasonDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllReasonDto dto = new FindAllReasonDto();
                dto.setIdReason(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setTypeReason(ValueUtil.getIntegerByObject(obj[2]));
                dto.setTimeCreated(ValueUtil.getLongByObject(obj[3]));
                dto.setTimeModified(ValueUtil.getLongByObject(obj[4]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                findAllReasonDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllReasonDtos, pageable, countFindAllReason(request));
    }

    private void setConditionFindAllReason(FindAllReasonsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (re.name REGEXP :keyword) ");
        }
        if (!Objects.isNull(request.getStatus())){
            sb.append(" and re.status = :status ");
        }
        if (!Objects.isNull(request.getTypeReason())){
            sb.append(" and re.type_reason = :typeReason ");
        }
    }
    private void setParameterFindAllReason(FindAllReasonsRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (!Objects.isNull(request.getTypeReason())){
            query.setParameter("typeReason", request.getTypeReason());
        }
    }

    private long countFindAllReason(FindAllReasonsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(0) count " +
                "from reason re " +
                "where 1 = 1 ");
        setConditionFindAllReason(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllReason(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setConditionFindAllTypeActionReason(FindAllTypeActionReasonsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (re.name REGEXP :keyword) ");
        }
        if (!Objects.isNull(request.getStatus())){
            sb.append(" and re.status = :status ");
        }
        if (!Objects.isNull(request.getTypeReason())){
            sb.append(" and re.type_reason = :typeReason ");
        }
        if (!Objects.isNull(request.getTypeAction())){
            sb.append(" and re.type_action = :typeAction ");
        }
    }

    private void setParameterFindAllTypeActionReason(FindAllTypeActionReasonsRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (!Objects.isNull(request.getTypeReason())){
            query.setParameter("typeReason", request.getTypeReason());
        }
        if (!Objects.isNull(request.getTypeAction())){
            query.setParameter("typeAction", request.getTypeAction());
        }
    }


    private long countFindAllTypeActionReason(FindAllTypeActionReasonsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(0) count " +
                " FROM reason re " +
                " WHERE re.type_action = :typeAction AND re.status = :status ");
        setConditionFindAllTypeActionReason(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeActionReason(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}
