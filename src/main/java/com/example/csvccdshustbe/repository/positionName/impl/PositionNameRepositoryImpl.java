package com.example.csvccdshustbe.repository.positionName.impl;

import com.example.csvccdshustbe.dto.positionName.FindAllPositionNameDto;
import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.entity.PositionName;
import com.example.csvccdshustbe.repository.positionName.PositionNameRepositoryCustom;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameRequest;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameVisibleRequest;
import com.example.csvccdshustbe.response.positionName.FindAllPositionNameVisibleResponse;
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

public class PositionNameRepositoryImpl implements PositionNameRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllPositionNameDto> findAllPositionNameStatus(Pageable pageable,
                                                                  FindAllPositionNameVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn " +
                "where 1=1 and pn.status = :status ");
        setConditionFindAllPositionNameByStatus(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllPositionNameByStatus(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllPositionNameDto> findAllPositionNameDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllPositionNameDto dto = new FindAllPositionNameDto();
                dto.setIdPositionName(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                findAllPositionNameDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllPositionNameDtos, pageable, countFindAllPositionNameStatus(request));
    }

    @Override
    public Page<FindAllPositionNameDto> findAllPositionName(Pageable pageable, FindAllPositionNameRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn " +
                "where 1 = 1  ");
        setConditionFindAllPositionName(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllPositionName(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllPositionNameDto> findAllPositionNameDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                FindAllPositionNameDto dto = new FindAllPositionNameDto();
                dto.setIdPositionName(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                findAllPositionNameDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllPositionNameDtos, pageable, countFindAllPositionName(request));
    }

    private long countFindAllPositionNameStatus(FindAllPositionNameVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_position as (      " +
                        "select pn.id_position_name, pn.name, pn.status, " +
                        "pn.time_created, pn.time_modified " +
                        "from position_name pn)   " +
                "   select count(cte.id_position_name) count   " +
                "   from cte_position cte      " +
                "   where 1 = 1 and cte.status = :status  ");
        setConditionFindAllPositionNameByStatus(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllPositionNameByStatus(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllPositionName(FindAllPositionNameRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_position as (      " +
                "select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn)   " +
                "   select count(cte.id_position_name) count   " +
                "   from cte_position cte      " +
                "   where 1 = 1 ");
        setConditionFindAllPositionName(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllPositionName(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private void setParameterFindAllPositionNameByStatus(FindAllPositionNameVisibleRequest request, Query query) {
        query.setParameter("status", Constants.POSITION_NAME_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setParameterFindAllPositionName(FindAllPositionNameRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
    }
    private void setConditionFindAllPositionNameByStatus(FindAllPositionNameVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword) ");
        }
    }

    private void setConditionFindAllPositionName(FindAllPositionNameRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP :keyword) ");
        }
        if (!Objects.isNull(request.getStatus())){
            sb.append(" and cte.status = :status ");
        }
    }
    @Override
    public Optional<PositionName> findPositionNameByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn " +
                "where 1=1 and pn.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                PositionName positionName = new PositionName();
                positionName.setIdPositionName(ValueUtil.getIntegerByObject(obj[0]));
                positionName.setName(ValueUtil.getStringByObject(obj[1]));
                positionName.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                positionName.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                positionName.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                return Optional.of(positionName);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<PositionName> findPositionNameById(Integer idPositionName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn " +
                "where 1=1 and pn.id_position_name = :idPositionName ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idPositionName", idPositionName);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                PositionName positionName = new PositionName();
                positionName.setIdPositionName(ValueUtil.getIntegerByObject(obj[0]));
                positionName.setName(ValueUtil.getStringByObject(obj[1]));
                positionName.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                positionName.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                positionName.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                return Optional.of(positionName);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isCheckExitsAssetByIdPositionName(Integer idPositionName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset " +
                "from position_name pos " +
                "    inner join other_vehicle_transport_module ovtm on pos.id_position_name = ovtm.id_position_name " +
                "    inner join asset asset on ovtm.id_asset = asset.id_asset " +
                "where pos.id_position_name = :idPositionName ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idPositionName", idPositionName);
        return !CollectionUtils.isEmpty(query.getResultList());
    }

    @Override
    public List<PositionName> findPositionNameByListId(List<Integer> idPositionName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pn.id_position_name, pn.name, pn.status, " +
                "pn.time_created, pn.time_modified " +
                "from position_name pn " +
                "where 1=1 and pn.id_position_name in :idPositionName ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idPositionName", idPositionName);
        List<Object[]> result = query.getResultList();
        List<PositionName>positionNameList= new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                PositionName positionName = new PositionName();
                positionName.setIdPositionName(ValueUtil.getIntegerByObject(obj[0]));
                positionName.setName(ValueUtil.getStringByObject(obj[1]));
                positionName.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                positionName.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                positionName.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                positionNameList.add(positionName);
            }
        }
        return positionNameList;
    }
}
