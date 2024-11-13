package com.example.csvccdshustbe.repository.taskSendDetailMail.impl;

import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.repository.taskSendDetailMail.TaskSendDetailMailRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

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
                "where code_task_send_mail = :codeTaskSendMail ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeTaskSendMail", codeTaskSendMail);
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
                sendDetailMail.setIdOpened(ValueUtil.getIntegerByObject(obj[12]));
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
}
