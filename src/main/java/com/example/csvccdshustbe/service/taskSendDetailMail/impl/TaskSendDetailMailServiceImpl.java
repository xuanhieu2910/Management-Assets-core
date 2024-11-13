package com.example.csvccdshustbe.service.taskSendDetailMail.impl;

import com.example.csvccdshustbe.dto.taskSendDetailMail.TaskSendDetailMailDto;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.repository.taskSendDetailMail.TaskSendDetailMailRepository;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskSendDetailMailServiceImpl implements TaskSendDetailMailService {

    @Autowired
    TaskSendDetailMailRepository taskSendDetailMailRepository;

    @Override
    public TaskSendDetailMail saveTaskSendDetailMail(TaskSendDetailMail sendDetailMail) {
        return taskSendDetailMailRepository.save(sendDetailMail);
    }

    @Override
    public void updateTrackingTaskSendDetailMail(TaskSendDetailMailDto taskSendDetailMailDto) {
        Optional<TaskSendDetailMail> taskSendDetailMail =
                taskSendDetailMailRepository.findByCodeTaskSendMail(taskSendDetailMailDto.getCodeTaskSendMail());
        String currentTime = String.valueOf(new Date().getTime());
        if (taskSendDetailMail.isPresent()){
            if (taskSendDetailMail.get().getIsOpened().equals(Constants.STATUS_TASK_SEND_DETAIL_MAIL_NOT_YET_OPEN)) {
                taskSendDetailMail.get().setIsOpened(Constants.STATUS_TASK_SEND_DETAIL_MAIL_IS_OPEN);
                taskSendDetailMail.get().setOpenCount( (Constants.STATUS_TASK_SEND_DETAIL_MAIL_DEFAULT_COUNT_OPEN + 1));
                taskSendDetailMail.get().setTimeFirstOpen(currentTime);
                taskSendDetailMail.get().setIpAddressFirst(taskSendDetailMailDto.getIpAddressRemote());
                taskSendDetailMail.get().setDeviceFirst(taskSendDetailMailDto.getDevice());
                taskSendDetailMail.get().setOpeningSystemFirst(taskSendDetailMailDto.getDevice());
            } else {
                taskSendDetailMail.get().setOpenCount( (taskSendDetailMail.get().getOpenCount() + 1));
                taskSendDetailMail.get().setTimeLastOpen(currentTime);
                taskSendDetailMail.get().setIpAddressLast(taskSendDetailMailDto.getIpAddressRemote());
                taskSendDetailMail.get().setDeviceLast(taskSendDetailMailDto.getDevice());
                taskSendDetailMail.get().setOpeningSystemLast(taskSendDetailMailDto.getDevice());
            }
            taskSendDetailMailRepository.save(taskSendDetailMail.get());
        }
    }
}
