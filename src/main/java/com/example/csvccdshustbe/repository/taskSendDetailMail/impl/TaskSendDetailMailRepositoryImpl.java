package com.example.csvccdshustbe.repository.taskSendDetailMail.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.repository.taskSendDetailMail.TaskSendDetailMailRepositoryCustom;
import com.example.csvccdshustbe.request.taskSendDetailMail.FindAllTaskSendDetailMailRequest;
import com.example.csvccdshustbe.response.taskSendDetailMail.FindAllTaskSendDetailMailResponse;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskSendDetailMailRepositoryImpl implements TaskSendDetailMailRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<TaskSendDetailMail> findByCodeTaskSendMail(String codeTaskSendMail) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_task_send_detail, code_task_send_mail, id_user, " +
                "       address_from, address_to, address_cc, subject,  " +
                "       content, status, reason, time_created, time_modified, " +
                "       is_opened, open_count, time_first_open, ip_address_first, " +
                "       country_first, state_region_first, location_first, " +
                "       opening_system_first, device_first, time_last_open,  " +
                "       ip_address_last, country_last, state_region_last,  " +
                "       location_last, opening_system_last, device_last, " +
                "       id_department_original " +
                "from task_send_detail_mail " +
                "where code_task_send_mail = :codeTaskSendMail  " +
                " and id_user = :idUser " +
                " and id_department_original = :idDepartmentOriginal ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("codeTaskSendMail", codeTaskSendMail);
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TaskSendDetailMail sendDetailMail = new TaskSendDetailMail();
                sendDetailMail.setIdTaskSendDetail(ValueUtil.getIntegerByObject(obj[0]));
                sendDetailMail.setCodeTaskSendMail(ValueUtil.getStringByObject(obj[1]));
                sendDetailMail.setIdUser(ValueUtil.getIntegerByObject(obj[2]));
                sendDetailMail.setAddressFrom(ValueUtil.getStringByObject(obj[3]));
                sendDetailMail.setAddressTo(ValueUtil.getStringByObject(obj[4]));
                sendDetailMail.setAddressCc(ValueUtil.getStringByObject(obj[5]));
                sendDetailMail.setSubject(ValueUtil.getStringByObject(obj[6]));
                sendDetailMail.setContent(ValueUtil.getStringByObject(obj[7]));
                sendDetailMail.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                sendDetailMail.setReason(ValueUtil.getStringByObject(obj[9]));
                sendDetailMail.setTimeCreated(ValueUtil.getStringByObject(obj[10]));
                sendDetailMail.setTimeModified(ValueUtil.getStringByObject(obj[11]));
                sendDetailMail.setIsOpened(ValueUtil.getIntegerByObject(obj[12]));
                sendDetailMail.setOpenCount(ValueUtil.getIntegerByObject(obj[13]));
                sendDetailMail.setTimeFirstOpen(ValueUtil.getStringByObject(obj[14]));
                sendDetailMail.setIpAddressFirst(ValueUtil.getStringByObject(obj[15]));
                sendDetailMail.setCountryFirst(ValueUtil.getStringByObject(obj[16]));
                sendDetailMail.setStateRegionFirst(ValueUtil.getStringByObject(obj[17]));
                sendDetailMail.setLocationFirst(ValueUtil.getStringByObject(obj[18]));
                sendDetailMail.setOpeningSystemFirst(ValueUtil.getStringByObject(obj[19]));
                sendDetailMail.setDeviceFirst(ValueUtil.getStringByObject(obj[20]));
                sendDetailMail.setTimeLastOpen(ValueUtil.getStringByObject(obj[21]));
                sendDetailMail.setIpAddressLast(ValueUtil.getStringByObject(obj[22]));
                sendDetailMail.setCountryLast(ValueUtil.getStringByObject(obj[23]));
                sendDetailMail.setStateRegionLast(ValueUtil.getStringByObject(obj[24]));
                sendDetailMail.setLocationLast(ValueUtil.getStringByObject(obj[25]));
                sendDetailMail.setOpeningSystemLast(ValueUtil.getStringByObject(obj[26]));
                sendDetailMail.setDeviceLast(ValueUtil.getStringByObject(obj[27]));
                sendDetailMail.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[28]));
                return Optional.of(sendDetailMail);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllTaskSendDetailMailResponse>
    findAllTaskSendDetailMail(Pageable pageable, FindAllTaskSendDetailMailRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_task_send_detail, code_task_send_mail, id_user,    " +
                "        address_from, address_to, address_cc, subject,     " +
                "        status, reason, time_created, time_modified,    " +
                "        is_opened, open_count, time_first_open, ip_address_first,    " +
                "        country_first, state_region_first, location_first,    " +
                "        opening_system_first, device_first, time_last_open,     " +
                "        ip_address_last, country_last, state_region_last,     " +
                "        location_last, opening_system_last, device_last,    " +
                "        id_department_original    " +
                " from task_send_detail_mail    " +
                " where 1 = 1  " +
                "  and id_user = :idUser " +
                "  and id_department_original = :idDepartmentOriginal ");
        setConditionFindAllTaskSendDetailMail(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTaskSendDetailMail(query, request);
        PageUtils.buildQuery(pageable, query);
        List<FindAllTaskSendDetailMailResponse> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllTaskSendDetailMailResponse response = new FindAllTaskSendDetailMailResponse();
                response.setIdTaskSendDetail(ValueUtil.getIntegerByObject(obj[0]));
                response.setCodeTaskSendMail(ValueUtil.getStringByObject(obj[1]));
                response.setIdUser(ValueUtil.getIntegerByObject(obj[2]));
                response.setAddressFrom(ValueUtil.getStringByObject(obj[3]));
                response.setAddressTo(ValueUtil.getStringByObject(obj[4]));
                response.setAddressCc(ValueUtil.getStringByObject(obj[5]));
                response.setSubject(ValueUtil.getStringByObject(obj[6]));
                response.setStatus(ValueUtil.getIntegerByObject(obj[7]));
                response.setReason(ValueUtil.getStringByObject(obj[8]));
                response.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                response.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                response.setIsOpened(ValueUtil.getIntegerByObject(obj[11]));
                response.setOpenCount(ValueUtil.getIntegerByObject(obj[12]));
                response.setTimeFirstOpen(ValueUtil.getStringByObject(obj[13]));
                response.setIpAddressFirst(ValueUtil.getStringByObject(obj[14]));
                response.setCountryFirst(ValueUtil.getStringByObject(obj[15]));
                response.setStateRegionFirst(ValueUtil.getStringByObject(obj[16]));
                response.setLocationFirst(ValueUtil.getStringByObject(obj[17]));
                response.setOpeningSystemFirst(ValueUtil.getStringByObject(obj[18]));
                response.setDeviceFirst(ValueUtil.getStringByObject(obj[19]));
                response.setTimeLastOpen(ValueUtil.getStringByObject(obj[20]));
                response.setIpAddressLast(ValueUtil.getStringByObject(obj[21]));
                response.setCountryLast(ValueUtil.getStringByObject(obj[22]));
                response.setStateRegionLast(ValueUtil.getStringByObject(obj[23]));
                response.setLocationLast(ValueUtil.getStringByObject(obj[24]));
                response.setOpeningSystemLast(ValueUtil.getStringByObject(obj[25]));
                response.setDeviceLast(ValueUtil.getStringByObject(obj[26]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllTaskSendMailDetail(request));
    }

    @Transactional
    @Modifying
    @Override
    public void deleteTaskSendDetailMailByIds(List<Integer> idsTaskSendMail) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from task_send_detail_mail " +
                "where id_task_send_detail in (:idsTaskSendDetailMail) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTaskSendDetailMail", idsTaskSendMail);
        query.executeUpdate();
    }

    private long countFindAllTaskSendMailDetail(FindAllTaskSendDetailMailRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                " from task_send_detail_mail    " +
                " where 1 = 1  " +
                "   and id_user = :idUser  " +
                "   and id_department_original = :idDepartmentOriginal ");
        setConditionFindAllTaskSendDetailMail(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTaskSendDetailMail(query, request);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllTaskSendDetailMail(Query query, FindAllTaskSendDetailMailRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
        if (StringUtils.isNotBlank(request.getSubject())){
            query.setParameter("subject", request.getSubject());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (ObjectUtils.isNotEmpty(request.getIsOpened())){
            query.setParameter("isOpened", request.getIsOpened());
        }
    }

    private void setConditionFindAllTaskSendDetailMail(StringBuilder sb, FindAllTaskSendDetailMailRequest request) {
        if (StringUtils.isNotBlank(request.getSubject())){
            sb.append(" and (subject REGEXP :subject ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and status = :status ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsOpened())){
            sb.append(" and is_opened = :isOpened ");
        }
        sb.append(" order by id_task_send_detail ");
    }
}
