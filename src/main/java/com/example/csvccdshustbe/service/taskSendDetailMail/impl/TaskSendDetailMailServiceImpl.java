package com.example.csvccdshustbe.service.taskSendDetailMail.impl;

import com.example.csvccdshustbe.dto.taskSendDetailMail.TaskSendDetailMailDto;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.entity.TaskSendMail;
import com.example.csvccdshustbe.repository.taskSendDetailMail.TaskSendDetailMailRepository;
import com.example.csvccdshustbe.request.taskSendDetailMail.FindAllTaskSendDetailMailRequest;
import com.example.csvccdshustbe.request.taskSendDetailMail.UpdateTaskSendDetailMailRequest;
import com.example.csvccdshustbe.response.taskSendDetailMail.FindAllTaskSendDetailMailResponse;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import com.example.csvccdshustbe.service.taskSendMail.TaskSendMailService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.EmailUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.PropertiesUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskSendDetailMailServiceImpl implements TaskSendDetailMailService {

    @Autowired
    TaskSendDetailMailRepository taskSendDetailMailRepository;

    @Lazy
    @Autowired
    TaskSendMailService taskSendMailService;

    @Override
    public TaskSendDetailMail saveTaskSendDetailMail(TaskSendDetailMail sendDetailMail) {
        return taskSendDetailMailRepository.save(sendDetailMail);
    }

    @Override
    public void updateTrackingTaskSendDetailMail(UpdateTaskSendDetailMailRequest taskSendDetailMailRequest) {
        Optional<TaskSendDetailMail> taskSendDetailMail =
                taskSendDetailMailRepository.findByCodeTaskSendMail(taskSendDetailMailRequest.getCodeTaskSendMail());
        String currentTime = String.valueOf(new Date().getTime());
        if (taskSendDetailMail.isPresent()){
            if (taskSendDetailMail.get().getIsOpened().equals(Constants.STATUS_TASK_SEND_DETAIL_MAIL_NOT_YET_OPEN)) {
                taskSendDetailMail.get().setIsOpened(Constants.STATUS_TASK_SEND_DETAIL_MAIL_IS_OPEN);
                taskSendDetailMail.get().setOpenCount( (Constants.STATUS_TASK_SEND_DETAIL_MAIL_DEFAULT_COUNT_OPEN + 1));
                taskSendDetailMail.get().setTimeFirstOpen(currentTime);
                taskSendDetailMail.get().setIpAddressFirst(taskSendDetailMailRequest.getIpAddressRemote());
                taskSendDetailMail.get().setDeviceFirst(taskSendDetailMailRequest.getDevice());
                taskSendDetailMail.get().setOpeningSystemFirst(taskSendDetailMailRequest.getDevice());
            } else {
                taskSendDetailMail.get().setOpenCount( (taskSendDetailMail.get().getOpenCount() + 1));
                taskSendDetailMail.get().setTimeLastOpen(currentTime);
                taskSendDetailMail.get().setIpAddressLast(taskSendDetailMailRequest.getIpAddressRemote());
                taskSendDetailMail.get().setDeviceLast(taskSendDetailMailRequest.getDevice());
                taskSendDetailMail.get().setOpeningSystemLast(taskSendDetailMailRequest.getDevice());
            }
            taskSendDetailMailRepository.save(taskSendDetailMail.get());
        }
    }

    @Override
    public Page<FindAllTaskSendDetailMailResponse> findAllTaskSendDetail(FindAllTaskSendDetailMailRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return taskSendDetailMailRepository.findAllTaskSendDetailMail(pageable, request);
    }

    @Override
    public void deleteTaskSendDetailMailByIds(List<Integer> idsTaskSendMail) {
        taskSendDetailMailRepository.deleteTaskSendDetailMailByIds(idsTaskSendMail);
    }

    @Transactional
    @Override
    public void reSendTaskSendDetailMail(String codeTaskSendMail) {
        Optional<TaskSendDetailMail> taskSendDetailMail = taskSendDetailMailRepository.findByCodeTaskSendMail(codeTaskSendMail);
        if (taskSendDetailMail.isEmpty()){
            throw new NotFoundException("Don't exits task send detail mail by code task send mail!");
        }
        TaskSendMail reTaskSendMail = reContructionTaskSendMail(taskSendDetailMail.get());
        taskSendDetailMailRepository.delete(taskSendDetailMail.get());
        taskSendMailService.saveTaskSendMail(reTaskSendMail);
    }

    private TaskSendMail reContructionTaskSendMail(TaskSendDetailMail taskSendDetailMail) {
        TaskSendMail reSendMail = new TaskSendMail();
        reSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
        reSendMail.setAddressFrom(taskSendDetailMail.getAddressFrom());
        reSendMail.setAddressTo(taskSendDetailMail.getAddressTo());
        reSendMail.setAddressCc(taskSendDetailMail.getAddressCc());
        reSendMail.setSubject(taskSendDetailMail.getSubject());
        reSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
        reSendMail.setContent(taskSendDetailMail.getContent());
        reSendMail.setRetry(Constants.INIT_RETRY);
        String timeCurrent = String.valueOf(new Date().getTime());
        reSendMail.setTimeCreated(timeCurrent);
        reSendMail.setTimeModified(timeCurrent);
        return reSendMail;
    }
}
