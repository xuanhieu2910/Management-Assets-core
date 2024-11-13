package com.example.csvccdshustbe.service.taskSendMail.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.entity.TaskSendMail;
import com.example.csvccdshustbe.repository.taskSendMail.TaskSendMailRepository;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import com.example.csvccdshustbe.service.taskSendMail.TaskSendMailService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskSendMailServiceImpl implements TaskSendMailService {

    @Autowired
    TaskSendMailRepository taskSendMailRepository;

    @Autowired
    TaskSendDetailMailService taskSendDetailMailService;

    @Override
    public void saveTaskSendMail(TaskSendMail taskSendMail) {
        taskSendMailRepository.save(taskSendMail);
        taskSendDetailMailService.saveTaskSendDetailMail(contructionCreateTaskSendDetailMail(taskSendMail));
    }

    private TaskSendDetailMail contructionCreateTaskSendDetailMail(TaskSendMail taskSendMail) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication();
        String timeCurrent = String.valueOf(new Date().getTime());
        TaskSendDetailMail sendDetailMail = new TaskSendDetailMail();
        sendDetailMail.setCodeTaskSendMail(taskSendMail.getCodeTaskSendMail());
        sendDetailMail.setIdUser(csvcUser.getIdUser());
        sendDetailMail.setAddressFrom(taskSendMail.getAddressFrom());
        sendDetailMail.setAddressTo(taskSendMail.getAddressTo());
        sendDetailMail.setAddressCc(taskSendMail.getAddressCc());
        sendDetailMail.setSubject(taskSendMail.getSubject());
        sendDetailMail.setContent(taskSendMail.getContent());
        sendDetailMail.setStatus(Constants.STATUS_TASK_SEND_MAIL_DETAIL_PENDING);
        sendDetailMail.setReason(null);
        sendDetailMail.setTimeCreated(timeCurrent);
        sendDetailMail.setTimeModified(timeCurrent);
        sendDetailMail.setIdOpened(Constants.STATUS_TASK_SEND_DETAIL_MAIL_NOT_YET_OPEN);
        sendDetailMail.setOpenCount(Constants.STATUS_TASK_SEND_DETAIL_MAIL_DEFAULT_COUNT_OPEN);
        sendDetailMail.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        return sendDetailMail;
    }
}
