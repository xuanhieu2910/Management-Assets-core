package com.example.csvccdshustbe.repository.user.impl;

import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.enums.ContextLevelPattern;
import com.example.csvccdshustbe.repository.user.CsvcUserRepositoryCustom;
import com.example.csvccdshustbe.request.user.FindAllUserRequest;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import com.example.csvccdshustbe.request.user.FindDetailsUserRequest;
import com.example.csvccdshustbe.response.user.FindAllUserResponse;
import com.example.csvccdshustbe.response.user.FindAllUserRoleDepartmentResponse;
import com.example.csvccdshustbe.response.user.FindDetailsUserResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
@Repository
public class CsvcUserRepositoryImpl implements CsvcUserRepositoryCustom {

    private final static String Extension_privilege = ",";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<CsvcUser> loadUserByUsername(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select  csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "        csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number,   " +
                "        csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified,    " +
                "        csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access,    " +
                "        csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived,    " +
                "        role.id_role, role.title, role.status, role.content, role.short_name, role.description,    " +
                "        role.time_created, role.time_modified, " +
                "        capabilities.name, capabilities.cap_type, capabilities.component " +
                " from csvc_user csvcUser    " +
                "          inner join user_role userRole on csvcUser.id_user = userRole.id_user    " +
                "          inner join role role on userRole.id_role = role.id_role    " +
                "          inner join role_capabilities roleCapabilities on role.id_role = roleCapabilities.id_role " +
                "          inner join capabilities capabilities on roleCapabilities.id_capabilities = capabilities.id_capability " +
                " where csvcUser.user_name = :userName " +
                "       and role.status = 1 and roleCapabilities.permission = 1 and capabilities.status = 1 " +
                "       and userRole.picked = :isPicked " +
                " group by csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "          csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number, " +
                "          csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified, " +
                "          csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access, " +
                "          csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "          role.id_role, role.title, role.status, role.content, role.short_name, role.description, " +
                "          role.time_created, role.time_modified, " +
                "          capabilities.name, capabilities.cap_type, capabilities.component ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", username);
        query.setParameter("isPicked", Constants.ROLE_USER_PICKED);
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
        sb.append(" select  csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "        csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number,    " +
                "        csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified,    " +
                "        csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access,    " +
                "        csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived,    " +
                "        role.id_role, role.title, role.status, role.content, role.short_name, role.description,    " +
                "        role.time_created, role.time_modified, " +
                "        capabilities.name, capabilities.cap_type, capabilities.component " +
                " from csvc_user csvcUser    " +
                "          inner join user_role userRole on csvcUser.id_user = userRole.id_user    " +
                "          inner join role role on userRole.id_role = role.id_role    " +
                "          inner join role_capabilities roleCapabilities on role.id_role = roleCapabilities.id_role " +
                "          inner join capabilities capabilities on roleCapabilities.id_capabilities = capabilities.id_capability " +
                " where csvcUser.id_user = :idUser " +
                "       and role.status = 1 and roleCapabilities.permission = 1 and capabilities.status = 1 " +
                "       and userRole.picked = :isPicked " +
                " group by csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "          csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number, " +
                "          csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified, " +
                "          csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access, " +
                "          csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "          role.id_role, role.title, role.status, role.content, role.short_name, role.description, " +
                "          role.time_created, role.time_modified, " +
                "          capabilities.name, capabilities.cap_type, capabilities.component ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUser", idUser);
        query.setParameter("isPicked",  Constants.ROLE_USER_PICKED);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            CsvcUser csvcUser = setCsvcUserLoadByUserName(result.get(0));
            csvcUser.setRole(getRolesLoadByUserName(result));
            return Optional.of(csvcUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CsvcUser> findByCodeCsvcUser(String codeUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select  csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "        csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number,    " +
                "        csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified,    " +
                "        csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access,    " +
                "        csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived,    " +
                "        role.id_role, role.title, role.status, role.content, role.short_name, role.description,    " +
                "        role.time_created, role.time_modified, " +
                "        capabilities.name, capabilities.cap_type, capabilities.component " +
                " from csvc_user csvcUser    " +
                "          inner join user_role userRole on csvcUser.id_user = userRole.id_user    " +
                "          inner join role role on userRole.id_role = role.id_role    " +
                "          inner join role_capabilities roleCapabilities on role.id_role = roleCapabilities.id_role " +
                "          inner join capabilities capabilities on roleCapabilities.id_capabilities = capabilities.id_capability " +
                " where csvcUser.code_user = :codeUser " +
                "       and role.status = 1 and roleCapabilities.permission = 1 and capabilities.status = 1 " +
                "       and userRole.picked = :isPicked " +
                " group by csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "          csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number, " +
                "          csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified, " +
                "          csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access, " +
                "          csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "          role.id_role, role.title, role.status, role.content, role.short_name, role.description, " +
                "          role.time_created, role.time_modified, " +
                "          capabilities.name, capabilities.cap_type, capabilities.component ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeUser", codeUser);
        query.setParameter("isPicked", Constants.ROLE_USER_PICKED);
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
        return !CollectionUtils.isEmpty(result);
    }

    @Override
    public Page<FindAllUserUsedDto> findAllUserUsedDto(FindAllUserUsedRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select csvcUser.user_name,    " +
                "       csvcUser.code_user,    " +
                "       csvcUser.full_name    " +
                "from csvc_user csvcUser  " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user  " +
                "    inner join department de on userRole.id_department = de.id_department  " +
                "where 1 = 1    " +
                "and csvcUser.is_actived = :isActive   " +
                "and de.id_department in (:idsDepartment) ");
        setConditionFindAllUserUsedDto(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUserUsedDto(request, query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllUserUsedDto>allUserUsedDtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllUserUsedDto dto = new FindAllUserUsedDto();
                dto.setUserName(ValueUtil.getStringByObject(obj[0]));
                dto.setCodeUser(ValueUtil.getStringByObject(obj[1]));
                dto.setFullName(ValueUtil.getStringByObject(obj[2]));
                allUserUsedDtos.add(dto);
            }
        }
        return new PageImpl<>(allUserUsedDtos, pageable, countFindAllUserUsedDto(request));
    }

    @Override
    public Optional<CsvcUser> findByUserName(String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select  csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "        csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number,    " +
                "        csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified,    " +
                "        csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access,    " +
                "        csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived,    " +
                "        role.id_role, role.title, role.status, role.content, role.short_name, role.description,    " +
                "        role.time_created, role.time_modified, " +
                "        capabilities.name, capabilities.cap_type, capabilities.component " +
                " from csvc_user csvcUser    " +
                "          inner join user_role userRole on csvcUser.id_user = userRole.id_user    " +
                "          inner join role role on userRole.id_role = role.id_role    " +
                "          inner join role_capabilities roleCapabilities on role.id_role = roleCapabilities.id_role " +
                "          inner join capabilities capabilities on roleCapabilities.id_capabilities = capabilities.id_capability " +
                " where csvcUser.user_name = :userName " +
                "       and role.status = 1 and roleCapabilities.permission = 1 and capabilities.status = 1 " +
                "       and userRole.picked = :isPicked " +
                " group by csvcUser.id_user, csvcUser.user_name, csvcUser.password, csvcUser.auth, " +
                "          csvcUser.code_user, csvcUser.full_name, csvcUser.phone_number, " +
                "          csvcUser.path_avatar, csvcUser.time_created, csvcUser.time_modified, " +
                "          csvcUser.delete_at, csvcUser.first_access, csvcUser.last_access, " +
                "          csvcUser.last_login, csvcUser.current_login, csvcUser.sex, csvcUser.is_actived, " +
                "          role.id_role, role.title, role.status, role.content, role.short_name, role.description, " +
                "          role.time_created, role.time_modified, " +
                "          capabilities.name, capabilities.cap_type, capabilities.component ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", userName);
        query.setParameter("isPicked", Constants.ROLE_USER_PICKED);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            CsvcUser csvcUser = setCsvcUserLoadByUserName(result.get(0));
            csvcUser.setRole(getRolesLoadByUserName(result));
            return Optional.of(csvcUser);
        }
        return Optional.empty();
    }

    @Override
    public List<Integer> findIdsUserByListUserName(List<String> userName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_user " +
                "from csvc_user where user_name in (:userName) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", userName);
        List<Integer> idsUser = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                idsUser.add(ValueUtil.getIntegerByObject(obj[0]));
            }
        }
        return idsUser;
    }

    @Modifying
    @Transactional
    @Override
    public void updateStatusAccountUserByIds(List<Integer> idsUser, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" update csvc_user set is_actived = :isActive " +
                "where id_user in (:ids) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isActive", status);
        query.setParameter("ids", idsUser);
        query.executeUpdate();
    }

    @Override
    public Page<FindAllUserResponse> findAllUser(FindAllUserRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select csvcUser.code_user, csvcUser.user_name, csvcUser.full_name, " +
                "       de.id_department, de.name, group_concat(role.short_name separator ';' ) roles " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join role role on userRole.id_role = role.id_role " +
                "    inner join department de on userRole.id_department = de.id_department " +
                "where de.id_department in (:idsDepartment) " +
                "and csvcUser.id_user != :idUserCurrent ");
        setConditionFindAllUser(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUser(request, query);
        PageUtils.buildPage(request.getPage(), request.getSize());
        List<Object[]> result = query.getResultList();
        List<FindAllUserResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllUserResponse response = new FindAllUserResponse();
                response.setCodeUser(ValueUtil.getStringByObject(obj[0]));
                response.setUserName(ValueUtil.getStringByObject(obj[1]));
                response.setFullName(ValueUtil.getStringByObject(obj[2]));
                response.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                response.setNameDepartment(ValueUtil.getStringByObject(obj[4]));
                response.setRoles(ValueUtil.getStringByObject(obj[5]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllUser(request));
    }

    @Override
    public Optional<FindDetailsUserResponse> findDetailsUserResponse(FindDetailsUserRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("select csvcUser.code_user, csvcUser.user_name, csvcUser.full_name,  " +
                "       csvcUser.phone_number, csvcUser.path_avatar,  " +
                "       userRole.id_user_role idUserRole,  " +
                "       de.id_department, de.name, de.code codeDeparment,  " +
                "       role.id_role idRole, role.short_name  " +
                "from csvc_user csvcUser  " +
                "      inner join user_role userRole on csvcUser.id_user = userRole.id_user  " +
                "      inner join role role on userRole.id_role = role.id_role  " +
                "      inner join department de on userRole.id_department = de.id_department  " +
                "where de.id_department in (:idsDepartment)  " +
                "and csvcUser.code_user = :codeUser");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsDepartment", request.getIdsDepartment());
        query.setParameter("codeUser", request.getCodeUser());
        List<Object[]> result = query.getResultList();
        FindDetailsUserResponse response = new FindDetailsUserResponse();
        List<FindAllUserRoleDepartmentResponse> roleDepartmentResponseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            setFindDetailsUser(response, result.get(0));
            for (Object[] obj : result){
                FindAllUserRoleDepartmentResponse roleDepartment = new FindAllUserRoleDepartmentResponse();
                roleDepartment.setIdUserRole(ValueUtil.getIntegerByObject(obj[5]));
                roleDepartment.setIdDepartment(ValueUtil.getIntegerByObject(obj[6]));
                roleDepartment.setNameDepartment(ValueUtil.getStringByObject(obj[7]));
                roleDepartment.setCodeDepartment(ValueUtil.getStringByObject(obj[8]));
                roleDepartment.setIdRole(ValueUtil.getIntegerByObject(obj[9]));
                roleDepartment.setNameRole(ValueUtil.getStringByObject(obj[10]));
                roleDepartmentResponseList.add(roleDepartment);
            }
            response.setUserRoleDepartment(roleDepartmentResponseList);
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Map<String, List<FindAllUserUsedDto>>
    findAllUserUsedToDownloadByIdsDepartment(List<Integer> idsDepartmentCurrent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name,  " +
                "       csvcUser.code_user, csvcUser.user_name, csvcUser.full_name, " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join department de on userRole.id_department = de.id_department " +
                "where csvcUser.is_actived = :isActive " +
                "and de.id_department in (:idsDepartment) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("isActive", Constants.ACCOUNT_IS_UN_LOCK);
        query.setParameter("idsDepartment", idsDepartmentCurrent);
        List<Object[]> result = query.getResultList();
        Map<String, List<FindAllUserUsedDto>> responses = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)){
            String keyword;
            Integer idDepartment;
            String nameDepartment;
            for (Object[] obj : result){
                idDepartment = ValueUtil.getIntegerByObject(obj[0]);
                nameDepartment = ValueUtil.getStringByObject(obj[1]);
                keyword = "STT_" + idDepartment + nameDepartment;
                keyword = ValueUtil.convertToVietnamese(keyword).replaceAll(ValueUtil.REGEX_letter_digit_period_underscore,
                        "");
                if (responses.containsKey(keyword)) {
                    FindAllUserUsedDto findAllUserUsedDto = new FindAllUserUsedDto();
                    findAllUserUsedDto.setCodeUser(ValueUtil.getStringByObject(obj[2]));
                    findAllUserUsedDto.setUserName(ValueUtil.getStringByObject(obj[3]));
                    findAllUserUsedDto.setFullName(ValueUtil.getStringByObject(obj[4]));
                    responses.get(keyword).add(findAllUserUsedDto);
                } else {
                    List<FindAllUserUsedDto> findAllUserUsedDtos = new ArrayList<>();
                    FindAllUserUsedDto findAllUserUsedDto = new FindAllUserUsedDto();
                    findAllUserUsedDto.setCodeUser(ValueUtil.getStringByObject(obj[2]));
                    findAllUserUsedDto.setUserName(ValueUtil.getStringByObject(obj[3]));
                    findAllUserUsedDto.setFullName(ValueUtil.getStringByObject(obj[4]));
                    findAllUserUsedDtos.add(findAllUserUsedDto);
                    responses.put(keyword, findAllUserUsedDtos);
                }
            }
        }
        return responses;
    }

    private void setFindDetailsUser(FindDetailsUserResponse response, Object[] obj) {
        response.setCodeUser(ValueUtil.getStringByObject(obj[0]));
        response.setUserName(ValueUtil.getStringByObject(obj[1]));
        response.setFullName(ValueUtil.getStringByObject(obj[2]));
        response.setPhoneNumber(ValueUtil.getStringByObject(obj[3]));
        response.setPathAvatar(ValueUtil.getStringByObject(obj[4]));
    }

    private long countFindAllUser(FindAllUserRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  count " +
                "from (select csvcUser.code_user, csvcUser.user_name, csvcUser.full_name,   " +
                "         de.id_department, de.name, group_concat(role.short_name, ';') roles   " +
                "from csvc_user csvcUser   " +
                "      inner join user_role userRole on csvcUser.id_user = userRole.id_user   " +
                "      inner join role role on userRole.id_role = role.id_role   " +
                "      inner join department de on userRole.id_department = de.id_department   " +
                "where de.id_department in (:idsDepartment)   " +
                "and csvcUser.id_user != :idUserCurrent   ");
        setConditionCountFindAllUser(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterCountFindAllUser(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterCountFindAllUser(FindAllUserRequest request, Query query) {
        query.setParameter("idsDepartment", request.getIdsDepartment());
        Integer idUserCurrent = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser();
        query.setParameter("idUserCurrent", idUserCurrent);
        if (StringUtils.isNotBlank(request.getFullName())){
            query.setParameter("fullName", request.getFullName());
        }
        if (Objects.nonNull(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (StringUtils.isNotBlank(request.getNameRole())){
            query.setParameter("shortNameRole", request.getNameRole());
        }
    }

    private void setConditionCountFindAllUser(FindAllUserRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getFullName())){
            sb.append(" and (csvcUser.full_name REGEXP  :fullName ) ");
        }
        if (Objects.nonNull(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getNameRole())){
            sb.append(" and ( role.short_name REGEXP  :shortNameRole ) ");
        }
        sb.append(" group by csvcUser.code_user, csvcUser.user_name, csvcUser.full_name, " +
                "         de.id_department, de.name ) result");
    }

    private void setParameterFindAllUser(FindAllUserRequest request, Query query) {
        query.setParameter("idsDepartment", request.getIdsDepartment());
        Integer idUserCurrent = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser();
        query.setParameter("idUserCurrent", idUserCurrent);
        if (StringUtils.isNotBlank(request.getFullName())){
            query.setParameter("fullName", request.getFullName());
        }
        if (Objects.nonNull(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (StringUtils.isNotBlank(request.getNameRole())){
            query.setParameter("shortNameRole", request.getNameRole());
        }
    }

    private void setConditionFindAllUser(FindAllUserRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getFullName())){
            sb.append(" and (csvcUser.full_name REGEXP  :fullName ) ");
        }
        if (Objects.nonNull(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getNameRole())){
            sb.append(" and ( role.short_name REGEXP  :shortNameRole ) ");
        }
        sb.append(" group by csvcUser.code_user, csvcUser.user_name, csvcUser.full_name, " +
                "         de.id_department, de.name ");
    }


    private long countFindAllUserUsedDto(FindAllUserUsedRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from csvc_user csvcUser " +
                "where 1 = 1 " +
                "and csvcUser.is_actived = :isActive ");
        setConditionFindAllUserUsedDto(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllUserUsedDto(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllUserUsedDto(FindAllUserUsedRequest request, Query query) {
        query.setParameter("isActive", Constants.ACCOUNT_IS_UN_LOCK);
        query.setParameter("idsDepartment", request.getIdsDepartment());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllUserUsedDto(FindAllUserUsedRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append("  and (csvcUser.user_name REGEXP :keyword ) ");
        }
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

    private List<Role> getRolesLoadByUserName(List<Object[]> result) {
        List<Role> roles = new ArrayList<>();
        Role role = createRole(result.get(0));
        role.setCapabilities(createCapabilities(result));
        roles.add(role);
        return roles;
    }

    private Set<Capabilities> createCapabilities(List<Object[]> result) {
        Set<Capabilities> capabilitiesSet = new HashSet<>();
        for (Object[] obj : result){
            Capabilities capa = new Capabilities();
            capa.setName(ValueUtil.getStringByObject(obj[25]));
            capa.setCapType(ValueUtil.getStringByObject(obj[26]));
            capa.setComponent(ValueUtil.getStringByObject(obj[27]));
            capabilitiesSet.add(capa);
        }
        return capabilitiesSet;
    }

    private Role createRole(Object[] obj) {
        Role role = new Role();
        role.setIdRole(ValueUtil.getIntegerByObject(obj[17]));
        role.setTitle(ValueUtil.getStringByObject(obj[18]));
        role.setStatus(ValueUtil.getIntegerByObject(obj[19]));
        role.setContent(ValueUtil.getStringByObject(obj[20]));
        role.setShortName(ValueUtil.getStringByObject(obj[21]));
        role.setDescription(ValueUtil.getStringByObject(obj[22]));
        role.setTimeCreated(ValueUtil.getStringByObject(obj[23]));
        role.setTimeModified(ValueUtil.getStringByObject(obj[24]));
        return role;
    }
}
