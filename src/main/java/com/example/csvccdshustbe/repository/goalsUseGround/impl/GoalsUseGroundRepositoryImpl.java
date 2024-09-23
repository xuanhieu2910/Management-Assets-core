package com.example.csvccdshustbe.repository.goalsUseGround.impl;

import com.example.csvccdshustbe.entity.GoalsUseGround;
import com.example.csvccdshustbe.repository.goalsUseGround.GoalsUseGroundRepositoryCustom;
import com.example.csvccdshustbe.request.goalsUseGround.FindAllGoalsUseGroundRequest;
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
import java.util.Optional;

public class GoalsUseGroundRepositoryImpl implements GoalsUseGroundRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<GoalsUseGround> findAllGoalsUseGroundActive(FindAllGoalsUseGroundRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select goalsUseGround.id_goals_use_ground, goalsUseGround.name, " +
                "       goalsUseGround.code,  " +
                "       goalsUseGround.status, goalsUseGround.time_created, " +
                "       goalsUseGround.time_modified " +
                "from goals_use_ground goalsUseGround " +
                "where goalsUseGround.status = :status ");
        setConditionFindAllGoalsUseGroundActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllGoalsUseGroundActive(request,query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<GoalsUseGround> goalsUseGrounds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                GoalsUseGround goalsUseGround = new GoalsUseGround();
                goalsUseGround.setIdGoalsUseGround(ValueUtil.getIntegerByObject(obj[0]));
                goalsUseGround.setName(ValueUtil.getStringByObject(obj[1]));
                goalsUseGround.setCode(ValueUtil.getStringByObject(obj[2]));
                goalsUseGround.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                goalsUseGround.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                goalsUseGround.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                goalsUseGrounds.add(goalsUseGround);
            }
        }
        return new PageImpl<>(goalsUseGrounds, pageable, countFindAllGoalsUseGroundActive(request));
    }


    private long countFindAllGoalsUseGroundActive(FindAllGoalsUseGroundRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from goals_use_ground goalsUseGround " +
                "where goalsUseGround.status = :status ");
        setConditionFindAllGoalsUseGroundActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllGoalsUseGroundActive(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllGoalsUseGroundActive(FindAllGoalsUseGroundRequest request, Query query) {
        query.setParameter("status", Constants.GOALS_USE_GROUND_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllGoalsUseGroundActive(FindAllGoalsUseGroundRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (medicineGroup.name REGEXP :keyword ) ");
        }
    }

    @Override
    public Optional<GoalsUseGround> findGoalsUseGroundByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select goalsUseGround.id_goals_use_ground, goalsUseGround.name, " +
                "       goalsUseGround.code,  " +
                "       goalsUseGround.status, goalsUseGround.time_created, " +
                "       goalsUseGround.time_modified " +
                "from goals_use_ground goalsUseGround " +
                "where goalsUseGround.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){

                GoalsUseGround goalsUseGround = new GoalsUseGround();
                goalsUseGround.setIdGoalsUseGround(ValueUtil.getIntegerByObject(obj[0]));
                goalsUseGround.setName(ValueUtil.getStringByObject(obj[1]));
                goalsUseGround.setCode(ValueUtil.getStringByObject(obj[2]));
                goalsUseGround.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                goalsUseGround.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                goalsUseGround.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(goalsUseGround);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<GoalsUseGround> findGoalsUseGroundById(Integer idGoalsUseGround){
        StringBuilder sb = new StringBuilder();
        sb.append(" select goalsUseGround.id_goals_use_ground, goalsUseGround.name, " +
                "       goalsUseGround.code,  " +
                "       goalsUseGround.status, goalsUseGround.time_created, " +
                "       goalsUseGround.time_modified " +
                "from goals_use_ground goalsUseGround " +
                "where goalsUseGround.id_goals_use_ground = :idGoalsUseGround ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGoalsUseGround", idGoalsUseGround);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){

                GoalsUseGround goalsUseGround = new GoalsUseGround();
                goalsUseGround.setIdGoalsUseGround(ValueUtil.getIntegerByObject(obj[0]));
                goalsUseGround.setName(ValueUtil.getStringByObject(obj[1]));
                goalsUseGround.setCode(ValueUtil.getStringByObject(obj[2]));
                goalsUseGround.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                goalsUseGround.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                goalsUseGround.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(goalsUseGround);
            }
        }
        return Optional.empty();
    }
}
