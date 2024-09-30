package com.example.csvccdshustbe.repository.role.impl;

import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.repository.role.RoleRepositoryCustom;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Role> findAllRoleDefault() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select role.id_role, role.title, role.status, " +
                "       role.content, role.short_name, role.description, " +
                "       role.time_created, role.time_modified, role.id_department " +
                "from role " +
                "where id_department = :isDefaultRole ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isDefaultRole", Constants.ROLE_DEFAULT);
        List<Object[]> result = query.getResultList();
        List<Role> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                Role role = new Role();
                role.setIdRole(ValueUtil.getIntegerByObject(obj[0]));
                role.setTitle(ValueUtil.getStringByObject(obj[1]));
                role.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                role.setContent(ValueUtil.getStringByObject(obj[3]));
                role.setShortName(ValueUtil.getStringByObject(obj[4]));
                role.setDescription(ValueUtil.getStringByObject(obj[5]));
                role.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                role.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                role.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
                roles.add(role);
            }
        }
        return roles;
    }

    @Override
    public Optional<Role> findByTitleRole(String titleRole) {
        StringBuilder sb = new StringBuilder();
        sb.append("select role.id_role, role.title, role.status,    " +
                "                         role.content, role.short_name, role.description,     " +
                "                         role.time_created, role.time_modified,   " +
                "                         capabilities.id_capability, capabilities.name, capabilities.cap_type,   " +
                "                         capabilities.status, capabilities.component,   " +
                "                         capabilities.time_created, capabilities.time_modified   " +
                "                 from role role   " +
                "                      inner join role_capabilities roleCapabilities on role.id_role = roleCapabilities.id_role   " +
                "                      inner join capabilities capabilities on roleCapabilities.id_capabilities = capabilities.id_capability   " +
                "                 where capabilities.status = 1   " +
                "                 and roleCapabilities.permission = 1   " +
                "                 and role.title = :titleRole  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("titleRole", titleRole);
        List<Object[]> results = query.getResultList();
        if (!CollectionUtils.isEmpty(results)) {
            Object[] roleResponse = results.get(0);
            Role role = new Role();
            role.setIdRole(ValueUtil.getIntegerByObject(roleResponse[0]));
            role.setTitle(ValueUtil.getStringByObject(roleResponse[1]));
            role.setStatus(ValueUtil.getIntegerByObject(roleResponse[2]));
            role.setContent(ValueUtil.getStringByObject(roleResponse[3]));
            role.setShortName(ValueUtil.getStringByObject(roleResponse[4]));
            role.setDescription(ValueUtil.getStringByObject(roleResponse[5]));
            role.setTimeCreated(ValueUtil.getStringByObject(roleResponse[6]));
            role.setTimeModified(ValueUtil.getStringByObject(roleResponse[7]));
            Set<Capabilities> capabilities = new HashSet<>();
            for(Object[] obj: results){
                Capabilities capability = new Capabilities();
                capability.setIdCapability(ValueUtil.getIntegerByObject(obj[8]));
                capability.setName(ValueUtil.getStringByObject(obj[9]));
                capability.setCapType(ValueUtil.getStringByObject(obj[10]));
                capability.setStatus(ValueUtil.getIntegerByObject(obj[11]));
                capability.setComponent(ValueUtil.getStringByObject(obj[12]));
                capability.setTimeCreated(ValueUtil.getStringByObject(obj[13]));
                capability.setTimeModified(ValueUtil.getStringByObject(obj[14]));
                capabilities.add(capability);
            }
            role.setCapabilities(capabilities);
            return Optional.of(role);
        }
        return Optional.empty();
    }
}
