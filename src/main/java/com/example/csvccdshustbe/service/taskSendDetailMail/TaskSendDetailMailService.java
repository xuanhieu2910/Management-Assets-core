package com.example.csvccdshustbe.service.taskSendDetailMail;

import com.example.csvccdshustbe.dto.taskSendDetailMail.TaskSendDetailMailDto;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;

public interface TaskSendDetailMailService {

    TaskSendDetailMail saveTaskSendDetailMail(TaskSendDetailMail sendDetailMail);
    void updateTrackingTaskSendDetailMail(TaskSendDetailMailDto taskSendDetailMailDto);
}
