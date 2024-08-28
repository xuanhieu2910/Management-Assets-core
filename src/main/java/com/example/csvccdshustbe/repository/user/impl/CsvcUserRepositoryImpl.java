package com.example.csvccdshustbe.repository.user.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Privilege;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.repository.user.CsvcUserRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class CsvcUserRepositoryImpl implements CsvcUserRepositoryCustom {

    private final static String Extension_privilege = ",";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<CsvcUser> loadUserByUsername(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select csvcUser.id_user, csvcUser.user_name, " +
                "       csvcUser.password, csvcUser.auth, csvcUser.code_user, " +
                "       csvcUser.full_name, csvcUser.phone_number, csvcUser.path_avatar, " +
                "       csvcUser.time_created, csvcUser.time_modified, csvcUser.delete_at, " +
                "       csvcUser.first_access, csvcUser.last_access, csvcUser.last_login, " +
                "       csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "       role.id_role, role.title, role.status, role.content, " +
                "       role.short_name, role.description, role.time_created, role.time_modified, " +
                "       group_concat(privilege.title, '') privilege_title " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join role role on role.id_role = userRole.id_role " +
                "    inner join role_privilege rolePrivilege on role.id_role = rolePrivilege.id_role " +
                "    inner join privilege privilege on rolePrivilege.id_privilege = privilege.id_privilege " +
                "and privilege.status = 1 " +
                "and role.status = 1 " +
                "and csvcUser.user_name = :userName " +
                "group by csvcUser.id_user, csvcUser.user_name, " +
                "         csvcUser.password, csvcUser.auth, csvcUser.code_user, " +
                "         csvcUser.full_name, csvcUser.phone_number, csvcUser.path_avatar, " +
                "         csvcUser.time_created, csvcUser.time_modified, csvcUser.delete_at, " +
                "         csvcUser.first_access, csvcUser.last_access, csvcUser.last_login, " +
                "         csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "         role.id_role, role.title, role.status, role.content, " +
                "         role.short_name, role.description, role.time_created, role.time_modified ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", username);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            CsvcUser csvcUser = setCsvcUserLoadByUserName(result.get(0));
            csvcUser.setRole(getRolesLoadByUserName(result));
            return Optional.of(csvcUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CsvcUser> findByIdCsvcUser(Integer idUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select csvcUser.id_user, csvcUser.user_name, " +
                "       csvcUser.password, csvcUser.auth, csvcUser.code_user, " +
                "       csvcUser.full_name, csvcUser.phone_number, csvcUser.path_avatar, " +
                "       csvcUser.time_created, csvcUser.time_modified, csvcUser.delete_at, " +
                "       csvcUser.first_access, csvcUser.last_access, csvcUser.last_login, " +
                "       csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "       role.id_role, role.title, role.status, role.content, " +
                "       role.short_name, role.description, role.time_created, role.time_modified, " +
                "       group_concat(privilege.title, '') privilege_title " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join role role on role.id_role = userRole.id_role " +
                "    inner join role_privilege rolePrivilege on role.id_role = rolePrivilege.id_role " +
                "    inner join privilege privilege on rolePrivilege.id_privilege = privilege.id_privilege " +
                "and privilege.status = 1 " +
                "and role.status = 1 " +
                "and csvcUser.id_user = :idUser " +
                "group by csvcUser.id_user, csvcUser.user_name, " +
                "         csvcUser.password, csvcUser.auth, csvcUser.code_user, " +
                "         csvcUser.full_name, csvcUser.phone_number, csvcUser.path_avatar, " +
                "         csvcUser.time_created, csvcUser.time_modified, csvcUser.delete_at, " +
                "         csvcUser.first_access, csvcUser.last_access, csvcUser.last_login, " +
                "         csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "         role.id_role, role.title, role.status, role.content, " +
                "         role.short_name, role.description, role.time_created, role.time_modified ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUser", idUser);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            CsvcUser csvcUser = setCsvcUserLoadByUserName(result.get(0));
            csvcUser.setRole(getRolesLoadByUserName(result));
            return Optional.of(csvcUser);
        }
        return Optional.empty();
    }

    @Override
    public Boolean exitsByUserName(String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select csvc_user.id_user, csvc_user.user_name " +
                "from csvc_user " +
                "where user_name = :userName ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", userName);
        List<Object[]> result = query.getResultList();
        return !CollectionUtils.isEmpty(result) ? true : false;
    }

    private CsvcUser setCsvcUserLoadByUserName(Object[] obj) {
        CsvcUser csvcUser = new CsvcUser();
        csvcUser.setIdUser(ValueUtil.getIntegerByObject(obj[0]));
        csvcUser.setUserName(ValueUtil.getStringByObject(obj[1]));
        csvcUser.setPassword(ValueUtil.getStringByObject(obj[2]));
        csvcUser.setAuth(ValueUtil.getStringByObject(obj[3]));
        csvcUser.setCodeUser(ValueUtil.getStringByObject(obj[4]));
        csvcUser.setFullName(ValueUtil.getStringByObject(obj[5]));
        csvcUser.setPhoneNumber(ValueUtil.getStringByObject(obj[6]));
        csvcUser.setPathAvatar(ValueUtil.getStringByObject(obj[7]));
        csvcUser.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
        csvcUser.setTimeModified(ValueUtil.getStringByObject(obj[9]));
        csvcUser.setDeleteAt(ValueUtil.getStringByObject(obj[10]));
        csvcUser.setFirstAccess(ValueUtil.getStringByObject(obj[11]));
        csvcUser.setLastAccess(ValueUtil.getStringByObject(obj[12]));
        csvcUser.setLastLogin(ValueUtil.getStringByObject(obj[13]));
        csvcUser.setCurrentLogin(ValueUtil.getStringByObject(obj[14]));
        csvcUser.setSex(ValueUtil.getIntegerByObject(obj[15]));
        csvcUser.setIsActived(ValueUtil.getIntegerByObject(obj[16]));
        return csvcUser;
    }

    private List<Role> getRolesLoadByUserName(List<Object[]> result){
        List<Role> roles = new ArrayList<>();
        for (Object[] obj: result){
            Role role = new Role();
            role.setIdRole(ValueUtil.getIntegerByObject(obj[17]));
            role.setTitle(ValueUtil.getStringByObject(obj[18]));
            role.setStatus(ValueUtil.getIntegerByObject(obj[19]));
            role.setContent(ValueUtil.getStringByObject(obj[20]));
            role.setShortName(ValueUtil.getStringByObject(obj[21]));
            role.setDescription(ValueUtil.getStringByObject(obj[22]));
            role.setTimeCreated(ValueUtil.getStringByObject(obj[23]));
            role.setTimeModified(ValueUtil.getStringByObject(obj[24]));
            String[] privileges = ValueUtil.getStringByObject(obj[25]).split(Extension_privilege);
            Set<Privilege> privilegeSet = new HashSet<>();
            for(String privilege : privileges){
                Privilege res = new Privilege();
                res.setTitle(privilege);
                privilegeSet.add(res);
            }
            role.setPrivileges(privilegeSet);
            roles.add(role);
        }
        return roles;
    }
}
