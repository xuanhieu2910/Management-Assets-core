package com.example.csvccdshustbe.repository.role.impl;

import com.example.csvccdshustbe.entity.Privilege;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.repository.role.RoleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Role> findByTitleRole(String titleRole) {
        StringBuilder sb = new StringBuilder();
        sb.append("select role.id_role, role.title, role.status, " +
                "       role.content, role.short_name, role.description, " +
                "       role.time_created, role.time_modified, " +
                "       privilege.id_privilege, privilege.title, privilege.status, " +
                "       privilege.short_name, privilege.description, privilege.slug, " +
                "       privilege.time_created, privilege.time_modified " +
                "from role role " +
                "    inner join role_privilege rolePrivilege on role.id_role = rolePrivilege.id_role " +
                "    inner join privilege privilege on rolePrivilege.id_privilege = privilege.id_privilege " +
                "where privilege.status = 1 " +
                "and rolePrivilege.status = 1 " +
                "and role.title = :titleRole ");
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
            Set<Privilege> privilegeSet = new HashSet<>();
            for(Object[] obj: results){
                Privilege privilege = new Privilege();
                privilege.setIdPrivilege(ValueUtil.getIntegerByObject(obj[8]));
                privilege.setTitle(ValueUtil.getStringByObject(obj[9]));
                privilege.setStatus(ValueUtil.getIntegerByObject(obj[10]));
                privilege.setShortName(ValueUtil.getStringByObject(obj[11]));
                privilege.setDescription(ValueUtil.getStringByObject(obj[12]));
                privilege.setSlug(ValueUtil.getStringByObject(obj[13]));
                privilege.setTimeCreated(ValueUtil.getStringByObject(obj[14]));
                privilege.setTimeModified(ValueUtil.getStringByObject(obj[15]));
                privilegeSet.add(privilege);
            }
            role.setPrivileges(privilegeSet);
            return Optional.of(role);
        }
        return Optional.empty();
    }
}
