package com.example.csvccdshustbe.repository.roleAllowAssign.impl;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.repository.roleAllowAssign.RoleAllowAssignRepositoryCustom;
import com.example.csvccdshustbe.response.roleAllowAssign.DestinationRoleAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowResponse;
import com.example.csvccdshustbe.response.roleAllowAssign.SourceRoleAssignResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleAllowAssignRepositoryImpl implements RoleAllowAssignRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssignResponse() {
        StringBuilder sb = new StringBuilder();
        sb.append("select role.id_role               idSourceRole, " +
                "       role.short_name            titleSourceRole, " +
                "       destinationRole.id_role    idDestinationRole, " +
                "       destinationRole.short_name titleDestinationRole, " +
                "       roleAllowAssign.status " +
                "from role role " +
                "         inner join role_allow_assign roleAllowAssign on role.id_role = roleAllowAssign.id_role " +
                "         inner join role destinationRole on roleAllowAssign.allow_assign = destinationRole.id_role " +
                "where role.title != :title ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("title", RolePattern.SuperAdmin.name());
        List<Object[]> result = query.getResultList();
        Map<Integer, FindAllRoleAllowAssignResponse> mapRoles = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)){
            Integer keyRole = null;
            for (Object[] obj : result){
                keyRole = ValueUtil.getIntegerByObject(obj[0]);
                if (mapRoles.containsKey(keyRole)) {
                    DestinationRoleAssignResponse desRole = new DestinationRoleAssignResponse();
                    desRole.setIdDestinationRoleAssign(ValueUtil.getIntegerByObject(obj[2]));
                    desRole.setNameDestinationRoleAssign(ValueUtil.getStringByObject(obj[3]));
                    desRole.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                    mapRoles.get(keyRole).getDestinationRoleAssignResponseList().add(desRole);
                } else {
                    FindAllRoleAllowAssignResponse allowAssignResponse = new FindAllRoleAllowAssignResponse();
                    SourceRoleAssignResponse souRole = new SourceRoleAssignResponse();
                    souRole.setIdSourceRoleAssign(ValueUtil.getIntegerByObject(obj[0]));
                    souRole.setNameSourceRoleAssign(ValueUtil.getStringByObject(obj[1]));
                    List<DestinationRoleAssignResponse> desRoles = new ArrayList<>();
                    DestinationRoleAssignResponse desRole = new DestinationRoleAssignResponse();
                    desRole.setIdDestinationRoleAssign(ValueUtil.getIntegerByObject(obj[2]));
                    desRole.setNameDestinationRoleAssign(ValueUtil.getStringByObject(obj[3]));
                    desRole.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                    desRoles.add(desRole);
                    allowAssignResponse.setSourceRoleAssignResponse(souRole);
                    allowAssignResponse.setDestinationRoleAssignResponseList(desRoles);
                    mapRoles.put(keyRole, allowAssignResponse);
                }
            }
        }
        return new ArrayList<>(mapRoles.values());
    }

    @Override
    public List<RoleAllowAssign> findAllRoleAllowAssign() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rl.id_role_allow_assign, " +
                "       rl.id_role, " +
                "       rl.allow_assign, " +
                "       rl.status " +
                "from role_allow_assign rl  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<RoleAllowAssign> roleAllowAssigns = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                RoleAllowAssign allowAssign = new RoleAllowAssign();
                allowAssign.setIdRoleAllowAssign(ValueUtil.getIntegerByObject(obj[0]));
                allowAssign.setIdRole(ValueUtil.getIntegerByObject(obj[1]));
                allowAssign.setAllowAssign(ValueUtil.getIntegerByObject(obj[2]));
                allowAssign.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                roleAllowAssigns.add(allowAssign);
            }
        }
        return roleAllowAssigns;
    }

    @Override
    public List<FindAllRoleAllowResponse> findAllRoleAllowAssignByTitleRole(String titleRole) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select destinationRole.id_role    idDestinationRole, " +
                "       destinationRole.short_name titleDestinationRole " +
                "       from role role " +
                "            inner join role_allow_assign roleAllowAssign on role.id_role = roleAllowAssign.id_role " +
                "            inner join role destinationRole on roleAllowAssign.allow_assign = destinationRole.id_role " +
                "where role.title = :title " +
                "and roleAllowAssign.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("title", titleRole);
        query.setParameter("status", Constants.ROLE_ALLOW_ASSIGN_STATUS);
        List<Object[]> result = query.getResultList();
        List<FindAllRoleAllowResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllRoleAllowResponse res = new FindAllRoleAllowResponse();
                res.setIdRole(ValueUtil.getIntegerByObject(obj[0]));
                res.setNameRole(ValueUtil.getStringByObject(obj[1]));
                responses.add(res);
            }
        }
        return responses;
    }
}
